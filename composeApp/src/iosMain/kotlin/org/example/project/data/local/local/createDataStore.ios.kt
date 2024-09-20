package org.example.project.data.local.local

//@OptIn(ExperimentalForeignApi::class)
//actual fun dataStorePreferences(
//    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
//    coroutineScope: CoroutineScope,
//    migrations: List<DataMigration<Preferences>>,
//): DataStore<Preferences> = createDataStoreWithDefaults(
//    corruptionHandler = corruptionHandler,
//    migrations = migrations,
//    coroutineScope = coroutineScope,
//    path = {
//        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
//            directory = NSDocumentDirectory,
//            inDomain = NSUserDomainMask,
//            appropriateForURL = null,
//            create = false,
//            error = null,
//        )
//        (requireNotNull(documentDirectory).path + "/$SETTINGS_PREFERENCES")
//    }
//)