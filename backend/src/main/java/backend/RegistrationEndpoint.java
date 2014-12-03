/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.inject.Named;

import static backend.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "registration", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend", ownerName = "backend", packagePath = ""))
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());
    private static final int SECRET_LENGTH = 40;
    private static final Random random = new Random();

    //TODO: Follow tutorial here https://cloud.google.com/tools/android_studio_templates/run_test_deploy

    /**
     * Register a device to the backend
     *
     * @param regId The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "register")
    public RegistrationRecord registerDevice(@Named("regId") String regId) {
        if (findRecord(regId) != null) {
            String message = "Device " + regId + " already registered, skipping register";
            log.info(message);
            throw new RuntimeException(message);
        }

        RegistrationRecord record = new RegistrationRecord();

        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int alphaLen = alphabet.length();
        StringBuilder secret = new StringBuilder();
        for (int i = 0; i < SECRET_LENGTH; ++i) {
            int index = random.nextInt(alphaLen);
            secret.append(alphabet.substring(index, index + 1));
        }
        record.setSecret(secret.toString());

        record.setRegId(regId);
        ofy().save().entity(record).now();

        return record;
    }

    /**
     * Unregister a device from the backend
     *
     * @param regId The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "unregister")
    public void unregisterDevice(@Named("regId") String regId, @Named("secret") String secret) {
        RegistrationRecord record = findRecord(regId);
        if (record == null) {
            log.info("Device " + regId + " not registered, skipping unregister");
            return;
        }
        if (secret == null || !secret.equals(record.getSecret())) {
            log.info("Device " + regId + " exists, but wrong secret key was provided. Unregister failed.");
            return;
        }
        ofy().delete().entity(record).now();
    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listDevices")
    public CollectionResponse<RegistrationRecord> listDevices(@Named("count") int count) {
        List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(count).list();
        for(RegistrationRecord r: records)
            r.setSecret(null);
        return CollectionResponse.<RegistrationRecord>builder().setItems(records).build();
    }

    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
    }

}
