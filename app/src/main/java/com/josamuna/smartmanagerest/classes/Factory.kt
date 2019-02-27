package com.josamuna.smartmanagerest.classes

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.StrictMode
import android.support.v4.app.FragmentManager
import android.support.v7.widget.PopupMenu
import android.util.Log
import android.widget.Toast
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.model.Equipment
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.sql.*

/**
 * Factory class for static access to common functions associated with Singleton Pattern
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
object Factory {
    var FRAGMENT_VALUE_TAG: FragmentTagValue = FragmentTagValue.DEFAULT
    var CONN_VALUE: Connection? = null
    var TEXT_MESSAGE: String = ""
    var FRAGMENTMANAGER: FragmentManager? = null
    var CLIPBOARDMANAGER: ClipboardManager? = null
    var CLIPDATA: ClipData? = null

    fun executeConnection(connectionClass: ConnectionClass): Connection? {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        Class.forName("net.sourceforge.jtds.jdbc.Driver")

        return DriverManager.getConnection(
            String.format(
                "jdbc:jtds:sqlserver://%s;databaseName=%s;user=%s;password=%s",
                connectionClass.serverName, connectionClass.database, connectionClass.userID, connectionClass.password
            )
        )
    }

    /**
     * Allow to close connection to Database
     */
    fun closeConnection(conn: Connection?) {
        if (conn != null) {
            if (!conn.isClosed)
                conn.close()
        }
    }

    /**
     * Allow to custom message to be set in a Toast
     */
    fun makeToastMessage(context: Context, strMessage: String, duration: ToastType) {
        when (duration) {
            ToastType.LONG -> Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show()
            ToastType.SHORT -> Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Allow to custom Log message with a particular type
     * Error Log
     * Debug Log
     * Information Log
     */
    fun makeLogMessage(tag: String, strMessage: String, logType: LogType) {
        when (logType) {
            LogType.INFORMATION -> Log.i(tag, strMessage)
            LogType.DEBUG -> Log.d(tag, strMessage)
            LogType.ERROR -> Log.e(tag, strMessage)
        }
    }

    /**
     * Allow to force popupMenu to get the Custom image
     */
    fun setForceShowIcon(popupMenu: PopupMenu) {
        val myFields: Array<Field> = popupMenu.javaClass.declaredFields
        for (field: Field in myFields) {
            if ("mPopup" == field.name) {
                field.isAccessible = true

                val menuPopupHelper: Any = field.get(popupMenu)
                val popupHelper: Class<*> = Class.forName(menuPopupHelper.javaClass.name)
                val mMethods: Method = popupHelper.getMethod("setForceShowIcon", Boolean::class.javaPrimitiveType)
                mMethods.invoke(menuPopupHelper, true)
                break
            }
        }
    }

    /**
     * Perform to find a specifique equipment data through his code passed has function argument
     */
    fun foundEquipement(context: Context, stringKey: String): Equipment? {
        var equipment: Equipment? = null

        if (stringKey.isEmpty())
            throw IllegalArgumentException("Empty string provided for search !!!")

        if (CONN_VALUE == null)
            throw SQLException("Make sure that you are connected to remote Database !!!")

        val query: String =
            "select materiel.id as idInt,materiel.code_str as idStr,categorie_materiel.designation as categorieMateriel,date_acquisition as acquisitionDate,garantie.valeur as guaranty,marque.designation as marque,\n" +
                    "modele.designation as model,couleur.designation as couleur,poids.valeur as weight,etat_materiel.designation as etat,materiel.label as label,\n" +
                    "materiel.mac_adresse1 as macWIFI, materiel.mac_adresse2 as macLAN,type_ordinateur.designation as typeComputer,type_clavier.designation as typeKeybord,OS.designation as operatingS,ram.id as ram,processeur.valeur as processeur,\n" +
                    "nombre_coeur_processeur.valeur as processorCore,type_hdd.designation as typeHDD,nombre_hdd.valeur as nbrHDD,capacite_hdd.valeur as capacityHDD,taille_ecran.valeur as sceenSize,usb2.valeur as usb2,usb3.valeur as usb3,hdmi.valeur as hdmi,\n" +
                    "vga.valeur as vga,tension_adaptateur.valeur as uBat,tension_adaptateur.valeur as uAdapt,puissance_adaptateur.valeur as pAdapt,materiel.numero_cle as numKey, intensite_adaptateur.valeur as iAdapt, \n" +
                    "materiel.commentaire as commentaire,materiel.archiver as archiver,\n" +
                    "\n" +
                    "type_switch.designation as typeSwitch,\n" +
                    "\n" +
                    "puissance.valeur as pImp,intensite.valeur as iImp,page_par_minute.valeur as ppm,type_imprimante.designation as typeImp,\n" +
                    "\n" +
                    "tension_alimentation.valeur as uAlim,usb.valeur as nbrUSB,memoire.valeur as nbrMemory,sorties_audio.valeur as nbrAudio,microphone.valeur as nbrMicro,gain.valeur as gain,type_amplificateur.designation as typeAmpl,\n" +
                    "\n" +
                    "gbe.valeur as gbe,fe.valeur as fe,fo.valeur as fo,serial.valeur as serial,default_pwd.designation as defaultPwd,default_ip.designation as defaultIP,console.valeur as console,auxiliaire.valeur as auxiliare,materiel.capable_usb as supportUSB, type_routeur_AP.designation as typeRouter, version_ios.designation as versionIOS,\n" +
                    "\n" +
                    "portee.valeur as range,type_AP.designation as typeAP,\n" +
                    "\n" +
                    "frequence.designation as frequencyRange,antenne.valeur as nbrAntenna,\n" +
                    "\n" +
                    "netette.designation as netete,materiel.compatible_wifi as supportWifi\n" +
                    "\n" +
                    "from materiel \n" +
                    "left outer join garantie on garantie.id=materiel.id_garantie\n" +
                    "left outer join categorie_materiel on categorie_materiel.id=materiel.id_categorie_materiel\n" +
                    "inner join marque on marque.id=materiel.id_marque\n" +
                    "inner join modele on modele.id=materiel.id_modele\n" +
                    "inner join couleur on couleur.id=materiel.id_couleur\n" +
                    "inner join poids on poids.id=materiel.id_poids\n" +
                    "inner join etat_materiel on etat_materiel.id=materiel.id_etat_materiel\n" +
                    "left outer join type_ordinateur on type_ordinateur.id=materiel.id_type_ordinateur\n" +
                    "left outer join type_clavier on type_clavier.id=materiel.id_type_clavier\n" +
                    "left outer join OS on OS.id=materiel.id_OS\n" +
                    "left outer join ram on ram.id=materiel.id_ram\n" +
                    "left outer join processeur on processeur.id=materiel.id_processeur\n" +
                    "left outer join nombre_coeur_processeur on nombre_coeur_processeur.id=materiel.id_nombre_coeur_processeur\n" +
                    "left outer join type_hdd on type_hdd.id=materiel.id_type_hdd\n" +
                    "left outer join nombre_hdd on nombre_hdd.id=materiel.id_nombre_hdd\n" +
                    "left outer join capacite_hdd on capacite_hdd.id=materiel.id_capacite_hdd\n" +
                    "left outer join taille_ecran on taille_ecran.id=materiel.id_taille_ecran\n" +
                    "left outer join usb2 on usb2.id=materiel.id_usb2\n" +
                    "left outer join usb3 on usb3.id=materiel.id_usb3\n" +
                    "left outer join hdmi on hdmi.id=materiel.id_hdmi\n" +
                    "left outer join vga on vga.id=materiel.id_vga\n" +
                    "left outer join tension_batterie on tension_batterie.id=materiel.id_tension_batterie\n" +
                    "left outer join tension_adaptateur on tension_adaptateur.id=materiel.id_tension_adaptateur\n" +
                    "left outer join puissance_adaptateur on puissance_adaptateur.id=materiel.id_puissance_adaptateur\n" +
                    "left outer join intensite_adaptateur on intensite_adaptateur.id=materiel.id_intensite_adaptateur\n" +
                    "\n" +
                    "--Printer\n" +
                    "left outer join type_imprimante on type_imprimante.id=materiel.id_type_imprimante\n" +
                    "left outer join puissance on puissance.id=materiel.id_puissance\n" +
                    "left outer join intensite on intensite.id=materiel.id_intensite\n" +
                    "left outer join page_par_minute on page_par_minute.id=materiel.id_page_par_minute\n" +
                    "\n" +
                    "--Amplificateur\n" +
                    "left outer join type_amplificateur on type_amplificateur.id=materiel.id_type_amplificateur\n" +
                    "left outer join tension_alimentation on tension_alimentation.id=materiel.id_tension_alimentation\n" +
                    "left outer join usb on usb.id=materiel.id_usb\n" +
                    "left outer join memoire on memoire.id=materiel.id_memoire\n" +
                    "left outer join sorties_audio on sorties_audio.id=materiel.id_sorties_audio\n" +
                    "left outer join microphone on microphone.id=materiel.id_microphone\n" +
                    "left outer join gain on gain.id=materiel.id_gain\n" +
                    "\n" +
                    "--Routeur_AP\n" +
                    "left outer join type_routeur_AP on type_routeur_AP.id=materiel.id_type_routeur_AP\n" +
                    "left outer join gbe on gbe.id=materiel.id_gbe\n" +
                    "left outer join fe on fe.id=materiel.id_fe\n" +
                    "left outer join fo on fo.id=materiel.id_fo\n" +
                    "left outer join serial on serial.id=materiel.id_serial\n" +
                    "left outer join default_pwd on default_pwd.id=materiel.id_default_pwd\n" +
                    "left outer join default_ip on default_ip.id=materiel.id_default_ip\n" +
                    "left outer join console on console.id=materiel.id_console\n" +
                    "left outer join auxiliaire on auxiliaire.id=materiel.id_auxiliaire\n" +
                    "left outer join version_ios on version_ios.id=materiel.id_version_ios\n" +
                    "--capable_usb\n" +
                    "\n" +
                    "--AccessPoint\n" +
                    "left outer join type_AP on type_AP.id=materiel.id_type_AP\n" +
                    "left outer join portee on portee.id=materiel.id_portee\n" +
                    "\n" +
                    "--Switch\n" +
                    "left outer join type_switch on type_switch.id=materiel.id_type_switch\n" +
                    "\n" +
                    "--Emetteur\n" +
                    "left outer join frequence on frequence.id=materiel.id_frequence\n" +
                    "left outer join antenne on antenne.id=materiel.id_antenne\n" +
                    "\n" +
                    "--Retroprojecteur\n" +
                    "left outer join netette on netette.id=materiel.id_netette\n" +
                    "where  materiel.code_str=?"

        val con: Connection = CONN_VALUE as Connection
        val ps: PreparedStatement = con.prepareStatement(query)
        ps.setString(1, stringKey)
        val dataResult: ResultSet = ps.executeQuery()

        if (dataResult.next()) {
            equipment = Equipment()

            //Add data
            equipment.idInt = dataResult.getInt("idInt")
            equipment.idStr = dataResult.getString("idStr")
            equipment.categorieMateriel = dataResult.getString("categorieMateriel")
            equipment.acquisitionDate = dataResult.getDate("acquisitionDate").toString()
            equipment.guaranty = dataResult.getInt("guaranty")
            equipment.marque = dataResult.getString("marque")
            equipment.model = dataResult.getString("model")
            equipment.couleur = dataResult.getString("couleur")
            equipment.weight = dataResult.getFloat("weight")
            equipment.etat = dataResult.getString("etat")
            equipment.label = dataResult.getString("label")

            equipment.macWIFI = dataResult.getString("macWIFI")
            equipment.macLAN = dataResult.getString("macLAN")
            equipment.typeComputer = dataResult.getString("typeComputer")
            equipment.typeKeybord = dataResult.getString("typeKeybord")
            equipment.operatingS = dataResult.getString("operatingS")
            equipment.ram = dataResult.getInt("ram")
            equipment.processeur = dataResult.getFloat("processeur")
            equipment.processorCore = dataResult.getInt("processorCore")
            equipment.typeHDD = dataResult.getString("typeHDD")
            equipment.nbrHDD = dataResult.getInt("nbrHDD")
            equipment.capacityHDD = dataResult.getInt("capacityHDD")
            equipment.sceenSize = dataResult.getFloat("sceenSize")
            equipment.usb2 = dataResult.getInt("usb2")
            equipment.usb3 = dataResult.getInt("usb3")
            equipment.hdmi = dataResult.getInt("hdmi")
            equipment.vga = dataResult.getInt("vga")
            equipment.uBat = dataResult.getFloat("uBat")
            equipment.uAdapt = dataResult.getFloat("uAdapt")
            equipment.pAdapt = dataResult.getFloat("pAdapt")
            equipment.iAdapt = dataResult.getFloat("iAdapt")
            equipment.numKey = dataResult.getString("numKey")
            equipment.commentaire = dataResult.getString("commentaire")
            equipment.archiver = dataResult.getBoolean("archiver")
            equipment.typeSwitch = dataResult.getString("typeSwitch")
            equipment.pImp = dataResult.getFloat("pImp")
            equipment.iImp = dataResult.getFloat("iImp")
            equipment.ppm = dataResult.getInt("ppm")
            equipment.typeImp = dataResult.getString("typeImp")
            equipment.uAlim = dataResult.getFloat("uAlim")
            equipment.nbrUSB = dataResult.getInt("nbrUSB")
            equipment.nbrMemory = dataResult.getInt("nbrMemory")
            equipment.nbrAudio = dataResult.getInt("nbrAudio")
            equipment.nbrMicro = dataResult.getInt("nbrMicro")
            equipment.gain = dataResult.getInt("gain")
            equipment.typeAmpl = dataResult.getString("typeAmpl")
            equipment.gbe = dataResult.getInt("gbe")
            equipment.fe = dataResult.getInt("fe")
            equipment.fo = dataResult.getInt("fo")
            equipment.serial = dataResult.getInt("serial")
            equipment.defaultPwd = dataResult.getString("defaultPwd")
            equipment.defaultIP = dataResult.getString("defaultIP")
            equipment.console = dataResult.getInt("console")
            equipment.auxiliare = dataResult.getInt("auxiliare")
            equipment.supportUSB = dataResult.getBoolean("supportUSB")
            equipment.typeRouter = dataResult.getString("typeRouter")
            equipment.versionIOS = dataResult.getString("versionIOS")
            equipment.range = dataResult.getInt("range")
            equipment.typeAP = dataResult.getString("typeAP")
            equipment.frequencyRange = dataResult.getString("frequencyRange")
            equipment.nbrAntenna = dataResult.getInt("nbrAntenna")
            equipment.netete = dataResult.getString("netete")
            equipment.supportWifi = dataResult.getBoolean("supportWifi")
        } else {
            makeToastMessage(
                context,
                "No data corresponding with that search, please provide a correct key",
                ToastType.LONG
            )
        }

        return equipment
    }
}