package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import fakesetgame.seniordesign.data.PlayerDataDbHelper;
import fakesetgame.seniordesign.data.Setting;

/**
 * Created by Chris on 10/27/2014.
 */
public class SettingsTest extends ApplicationTestCase<Application> {

    public SettingsTest() {
        super(Application.class);
    }

    public void testGetAndSaveSetting(){
        String testSettingName = "TEST SETTING 1283683214";
        String testSettingValue1 = "This is the test setting value";
        String testSettingValue2 = "This is another test setting value";

        Setting setting = PlayerDataDbHelper.getSetting(getContext(), testSettingName);
        Assert.assertNull(setting);

        // Test insert
        PlayerDataDbHelper.saveSetting(getContext(), testSettingName, testSettingValue1);
        setting = PlayerDataDbHelper.getSetting(getContext(), testSettingName);
        Assert.assertEquals(testSettingName, setting.getName());
        Assert.assertEquals(testSettingValue1, setting.getValue());

        // Test update
        PlayerDataDbHelper.saveSetting(getContext(), testSettingName, testSettingValue2);
        setting = PlayerDataDbHelper.getSetting(getContext(), testSettingName);
        Assert.assertEquals(testSettingName, setting.getName());
        Assert.assertEquals(testSettingValue2, setting.getValue());

        // Test delete
        PlayerDataDbHelper.deleteSetting(getContext(), testSettingName);
        setting = PlayerDataDbHelper.getSetting(getContext(), testSettingName);
        Assert.assertNull(setting);
    }
}
