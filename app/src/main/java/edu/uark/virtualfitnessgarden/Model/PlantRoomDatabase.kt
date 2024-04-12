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

@Database(entities = arrayOf(Plant::class), version = 1, exportSchema = false)
abstract class PlantRoomDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao

    private class TaskDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("Database", "PlantRoomDatabase.onCreate() // INSTANCE: $INSTANCE")

            INSTANCE?.let { database ->
                scope.launch {
                    val plantDao = database.plantDao()

                    // Delete all content here.
                    plantDao.deleteAll()

                    // Add Sample plants
                    var plant: Plant

                    plant = Plant(R.integer.plant_sunflower_id,
                                  R.drawable.img_plant_default_1,
                                  R.drawable.img_plant_default_2,
                                  R.drawable.img_plant_sunflower_3)
                    plantDao.insert(plant)

                    plant = Plant(R.integer.plant_rose_id,
                                  R.drawable.img_plant_default_1,
                                  R.drawable.img_plant_default_2,
                                  R.drawable.img_plant_rose_3)
                    plantDao.insert(plant)

                    plant = Plant(R.integer.plant_tulip_id,
                                  R.drawable.img_plant_default_1,
                                  R.drawable.img_plant_default_2,
                                  R.drawable.img_plant_tulip_3)
                    plantDao.insert(plant)

                    plant = Plant(R.integer.plant_cactus_id,
                                  R.drawable.img_plant_default_1,
                                  R.drawable.img_plant_default_2,
                                  R.drawable.img_plant_cactus_3)
                    plantDao.insert(plant)


                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PlantRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PlantRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantRoomDatabase::class.java,
                    "plantinfo_table"
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
