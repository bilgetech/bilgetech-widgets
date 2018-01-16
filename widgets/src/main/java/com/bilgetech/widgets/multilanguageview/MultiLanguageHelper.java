package com.bilgetech.widgets.multilanguageview;

import com.bilgetech.widgets.helper.VersionComparator;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class MultiLanguageHelper {
    public static final String DEFAULT_CULTURE = "tr";
    private static final String KEY_LANGUAGE_HELPER = "languageHelper";
    private static final String GLOBAL_PAGE = "Global";

    private static MultiLanguageHelper instance;
    private String version;
    private String culture;
    private Map<String, Map<String, String>> translationMap;

    public boolean isOlderThan(String otherVersion) {
        return VersionComparator.getVersionValue(version) < VersionComparator.getVersionValue(otherVersion);
    }

    public String translate(String key) {
        if(key == null) return null;

        if (getTranslationMap().containsKey(GLOBAL_PAGE) && translationMap.get(GLOBAL_PAGE).containsKey(key)) {
            return translationMap.get(GLOBAL_PAGE).get(key);
        } else {
            return key;
        }
    }

    public String translate(String page, String key) {
        if(page == null || key == null) return null;

        if (translationMap.containsKey(page) && translationMap.get(page).containsKey(key)) {
            return translationMap.get(page).get(key);
        } else {
            return translate(key);
        }
    }

    public String getVersion() {
        return version;
    }

    public MultiLanguageHelper setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getCulture() {
        return culture;
    }

    public MultiLanguageHelper setCulture(String culture) {
        this.culture = culture;
        return this;
    }

    public Map<String, Map<String, String>> getTranslationMap() {
        return translationMap;
    }

    public MultiLanguageHelper setTranslationMap(Map<String, Map<String, String>> translationMap) {
        this.translationMap = translationMap;
        return this;
    }

    public void save() {
        instance = this;
        Paper.book().write(KEY_LANGUAGE_HELPER, this);
    }

    public static void load() {
        instance = Paper.book().read(KEY_LANGUAGE_HELPER);
    }

    public static MultiLanguageHelper getInstance() {
        if (instance == null) {
            instance = new MultiLanguageHelper();
            instance.setVersion("0")
                    .setTranslationMap(new HashMap<String, Map<String, String>>())
                    .save();
        }
        return instance;
    }
}
