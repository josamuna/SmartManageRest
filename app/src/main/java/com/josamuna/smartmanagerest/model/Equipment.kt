package com.josamuna.smartmanagerest.model

class Equipment {
    var idInt: Int = 0
    var idStr: String = ""
    var categorieMateriel: String = ""
    var acquisitionDate: String = ""
    var guaranty: Int = 1
    var marque: String = ""
    var model: String = ""
    var couleur: String = ""
    var weight: Float = 1F
    var etat: String = ""
    var label: String = ""
    var macWIFI: String? = null
    var macLAN: String? = null
    var typeComputer: String? = null
    var typeKeybord: String? = null
    var operatingS: String? = null
    var ram: Int? = null
    var processeur: Float? = null
    var processorCore: Int? = null
    var typeHDD: String? = null
    var nbrHDD: Int? = null
    var capacityHDD: Int? = null
    var sceenSize: Float? = null
    var usb2: Int? = null
    var usb3: Int? = null
    var hdmi: Int? = null
    var vga: Int? = null
    var uBat: Float? = null
    var uAdapt: Float? = null
    var iAdapt: Float? = null
    var pAdapt: Float? = null
    var numKey: String? = null
    var commentaire: String? = null
    var archiver: Boolean = false

    var typeSwitch: String? = null
    var pImp: Float? = null
    var iImp: Float? = null
    var ppm: Int? = null
    var typeImp: String? = null

    var uAlim: Float? = null
    var nbrUSB: Int? = null
    var nbrMemory: Int? = null
    var nbrAudio: Int? = null
    var nbrMicro: Int? = null
    var gain: Int? = null
    var typeAmpl: String? = null

    var gbe: Int? = null
    var fe: Int? = null
    var fo: Int? = null
    var serial: Int? = null
    var console: Int? = null
    var auxiliare: Int? = null
    var defaultPwd: String? = null
    var supportUSB: Boolean = false
    var defaultIP: String? = null
    var typeRouter: String? = null
    var versionIOS: String? = null

    var range: Int? = null
    var typeAP: String? = null

    var frequencyRange: String? = null
    var nbrAntenna: Int? = null

    var netete: String? = null
    var supportWifi: Boolean? = null
}