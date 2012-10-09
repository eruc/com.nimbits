/*
 * Copyright (c) 2010 Nimbits Inc.
 *
 * http://www.nimbits.com
 *
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the license is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, eitherexpress or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.nimbits.server.transactions.memcache.settings;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.nimbits.client.enums.MemCacheKey;
import com.nimbits.client.enums.SettingType;
import com.nimbits.client.exception.NimbitsException;
import com.nimbits.server.transactions.service.settings.SettingTransactions;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by bsautner
 * User: benjamin
 * Date: 9/29/11
 * Time: 10:44 AM
 */
@SuppressWarnings("unchecked")
@Component("settingsCache")
public class SettingMemCacheImpl implements SettingTransactions {
    private final MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
    private SettingTransactions  settingsDao;


    private String SettingCacheKey(final SettingType setting) {
        return SettingType.serverVersion.getDefaultValue() +  MemCacheKey.setting + setting.getName();
    }

    @Override
    public String reloadCache() throws NimbitsException {

        final StringBuilder builder = new StringBuilder(1024);
        builder.append("<h5>Removing old values from memcache</h5>");


           // cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
            final Map<SettingType, String> settings =settingsDao.getSettings();
            if (cache.contains(MemCacheKey.allSettings)) {
                cache.delete(MemCacheKey.allSettings);
            }
            for (final SettingType setting : settings.keySet()) {

                if (setting != null) {

                    builder.append("Removed: ").append(setting.getName()).append("<br />");
                    if (cache.contains(SettingCacheKey(setting)))   {
                        cache.delete(SettingCacheKey(setting));
                    }
                }



            }


        return builder.toString();

    }


    @Override
    public String getSetting(final SettingType setting) throws NimbitsException {


            if (cache.contains(SettingCacheKey(setting))) {
                return (String) cache.get(SettingCacheKey(setting));

            } else {
                String storedVal = settingsDao.getSetting(setting);
                cache.put(SettingCacheKey(setting), storedVal);
                return storedVal;

            }


    }

    @Override
    public Map<SettingType, String> getSettings() throws NimbitsException {

               if (cache.contains(MemCacheKey.allSettings)) {
                return (Map<SettingType, String>) cache.get(MemCacheKey.allSettings);

            } else {
                final Map<SettingType, String> settings = settingsDao.getSettings();
                cache.put(MemCacheKey.allSettings, settings);
                return settings;
            }

    }

    @Override
    public void addSetting(final SettingType setting, final String value) throws NimbitsException {
        settingsDao.addSetting(setting, value);


            if (cache.contains(MemCacheKey.allSettings)) {
                cache.delete(MemCacheKey.allSettings);
            }
            if (cache.contains(SettingCacheKey(setting))) {
                cache.delete(SettingCacheKey(setting));
            }


    }

    @Override
    public void updateSetting(final SettingType setting, final String newValue) throws NimbitsException {
        settingsDao.updateSetting(setting, newValue);


            if (cache.contains(MemCacheKey.allSettings)) {
                cache.delete(MemCacheKey.allSettings);
            }
            if (cache.contains(SettingCacheKey(setting))) {
                cache.delete(SettingCacheKey(setting));
            }

    }


    public void setSettingsDao(SettingTransactions settingsDao) {
        this.settingsDao = settingsDao;
    }

    public SettingTransactions getSettingsDao() {
        return settingsDao;
    }
}
