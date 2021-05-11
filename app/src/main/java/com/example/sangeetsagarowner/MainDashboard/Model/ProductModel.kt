package com.example.sangeetsagarowner.MainDashboard.Model

class ProductModel {

    var item_name : String? = null
    var item_price : String? = null
    var item_model : String? = null
    var item_availability : String? = null
    var Imagetoken : String? = null

    constructor(){

    }

    constructor(item_name: String?, item_price: String?, item_model: String?, item_availability: String?, Imagetoken: String?) {
        this.item_name = item_name
        this.item_price = item_price
        this.item_model = item_model
        this.item_availability = item_availability
        this.Imagetoken = Imagetoken
    }


}