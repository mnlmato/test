package com.vp.core.datasource

import io.realm.Realm
import io.realm.RealmConfiguration

object RealmProvider {

    fun getRealmInstance(): Realm = Realm.getInstance(realmConfiguration())

    private fun realmConfiguration() = RealmConfiguration.Builder()
            .name(Realm.DEFAULT_REALM_NAME)
            .deleteRealmIfMigrationNeeded()
            .build()
}