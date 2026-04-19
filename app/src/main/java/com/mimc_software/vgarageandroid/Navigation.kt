package com.mimc_software.vgarageandroid

sealed class Navigation(val route:String) {
    object Landing:Navigation("landing")
    object AddGarage:Navigation("addGarage")
    object Main: Navigation("main")
    object AddVehicle: Navigation("addVehicle/{garageId}") {
        fun createRoute(garageId: String) = "addVehicle/$garageId"
    }
    object VehicleDetails: Navigation("vehicleDetails/{vehicleId}") {
        fun createRoute(vehicleId: String) = "vehicleDetails/$vehicleId"
    }
    object AddMaintenance: Navigation("addMaintenance/{vehicleId}") {
        fun createRoute(vehicleId: String) = "addMaintenance/$vehicleId"
    }
}