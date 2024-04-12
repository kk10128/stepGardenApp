package edu.uark.virtualfitnessgarden.Model

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.uark.virtualfitnessgarden.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PlantUser::class), version = 1, exportSchema = false)
abstract class PlantUserRoomDatabase : RoomDatabase() {

    abstract fun plantUserDao(): PlantUserDao

    private class TaskDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Database", "PlantUserRoomDatabase.onCreate()")

            Log.d("Database", "PlantUserRoomDatabase.onCreate() // INSTANCE: $INSTANCE")

            INSTANCE?.let { database ->
                scope.launch {
                    val plantUserDao = database.plantUserDao()

                    // Delete all content here.
                    plantUserDao.deleteAll()

                    addPlantsUser0(plantUserDao)
                    addPlantsUser1(plantUserDao)
                }
            }
        }

        suspend fun addPlantsUser0(plantUserDao: PlantUserDao){
            val user_id = 0
            var plantUser = PlantUser(R.integer.plant_sunflower_id, user_id, 0, 1, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_tulip_id, user_id, 1, 0, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_rose_id, user_id, 2, 2, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 3, 2, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_rose_id, user_id, 4, 2, 1)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_sunflower_id, user_id, 5, 1, 2)
            plantUserDao.insert(plantUser)
        }

        suspend fun addPlantsUser1(plantUserDao: PlantUserDao){
            val user_id = 1
            var plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 0, 1, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 1, 0, 2)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 2, 2, 2)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 3, 2, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 4, 2, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 5, 1, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 6, 1, 3)
            plantUserDao.insert(plantUser)

            plantUser = PlantUser(R.integer.plant_cactus_id, user_id, 7, 1, 2)
            plantUserDao.insert(plantUser)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PlantUserRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PlantUserRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantUserRoomDatabase::class.java,
                    "plantUserinfo_table"
                )
                    .addCallback(TaskDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
