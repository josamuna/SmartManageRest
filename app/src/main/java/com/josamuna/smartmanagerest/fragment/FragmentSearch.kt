package com.josamuna.smartmanagerest.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.josamuna.smartmanagerest.R
import com.josamuna.smartmanagerest.classes.Factory
import com.josamuna.smartmanagerest.enumerations.FragmentTagValue
import com.josamuna.smartmanagerest.enumerations.LogType
import com.josamuna.smartmanagerest.enumerations.ToastType
import com.josamuna.smartmanagerest.model.Equipment
import kotlinx.android.synthetic.main.fragment_search.*
import java.sql.SQLException

/**
 * Fragment that allow to search information from Database accordind QRCode early saved
 *
 *  @author Isamuna Nkembo Josue alias Josamuna
 *  @since Feb 2019
 */
class FragmentSearch : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Allow supporting scrolling in TextView
        val txtDataSearchQr: TextView = view.findViewById<View>(R.id.txtDataSearchedQrcode) as TextView
        txtDataSearchQr.movementMethod = ScrollingMovementMethod()

        //Set Fragment Title
        val supportAct: AppCompatActivity = activity as AppCompatActivity
        supportAct.supportActionBar?.title = getString(R.string.title_fragment_search)

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.SEARCH

        //Set value in search text
        if (Factory.TEXT_MESSAGE.isNotEmpty())
            edtQrCodeValue.setText(Factory.TEXT_MESSAGE)

        btn_found_item.setOnClickListener {
            //Perform fetching items from database
            try {
                val dataAff: StringBuilder = findFromRemoteDatabase(edtQrCodeValue.text.toString())
                txtDataSearchQr.text = dataAff.toString()
            } catch (e: IllegalArgumentException) {
                Factory.makeLogMessage(
                    "Error",
                    "Please specifie a valide key to perform search\n ${e.message}",
                    LogType.ERROR
                )
                Factory.makeToastMessage(context!!, "Please specifie a valide key to perform search", ToastType.LONG)
            } catch (e: SQLException) {
                Factory.makeLogMessage(
                    "Error",
                    "Unable to fetch data, be sure you are connected to remote Database\n ${e.message}",
                    LogType.ERROR
                )
                Factory.makeToastMessage(
                    context!!,
                    "Unable to fetch data, be sure you are connected to remote Database\n ${e.message}",
                    ToastType.LONG
                )
            } catch (e: Exception) {
                Factory.makeLogMessage(
                    "Error",
                    "Unable to fetch data, be sure you are connected to remote Database\n ${e.message}",
                    LogType.ERROR
                )
                Factory.makeToastMessage(
                    context!!,
                    "Unable to fetch data, be sure you are connected to remote Database\n ${e.message}",
                    ToastType.LONG
                )
            }
        }
    }

    private fun findFromRemoteDatabase(stringKey: String): StringBuilder {
        //Fetch data from remote Database and display then in custom format
        val stringReturn = StringBuilder()
        stringReturn.append("No data corresponding with that search, please provide a correct key")
        val equipment: Equipment? = Factory.foundEquipement(context!!, stringKey)

        if (equipment != null) {
            //Clear StringBuilder
            stringReturn.clear()

            //Format string after return that
            stringReturn.append("Equipment Category : ")
                .append(equipment.categorieMateriel).append("\n")
                .append("=====================================").append("\n")
                .append("1.Id               : ").append(equipment.idInt).append("\n")
                .append("2.Id text          : ").append(equipment.idStr).append("\n")
                .append("3.Aquisition Date  : ").append(equipment.acquisitionDate).append("\n")
                .append("4.Guaraty          : ").append(equipment.guaranty).append("\n")
                .append("5.Mark             : ").append(equipment.marque).append("\n")
                .append("6.Model            : ").append(equipment.model).append("\n")
                .append("7.Color            : ").append(equipment.couleur).append("\n")
                .append("8.Weight           : ").append(equipment.weight).append("\n")
                .append("9.State            : ").append(equipment.etat).append("\n")
                .append("10.Label           : ").append(equipment.label).append("\n")
                .append("11.Wifi MAC Addr.  : ").append(equipment.macWIFI).append("\n")
                .append("12.Lan MAC Addr.   : ").append(equipment.macLAN).append("\n")
                .append("13.Type Computer   : ").append(equipment.typeComputer).append("\n")
                .append("14.Keybord         : ").append(equipment.typeKeybord).append("\n")
                .append("15.Operating Syst. : ").append(equipment.operatingS).append("\n")
                .append("16.Ram(Gb)         : ").append(equipment.ram).append("\n")
                .append("17.Processor(Ghz)  : ").append(equipment.processeur).append("\n")
                .append("18.Processor core  : ").append(equipment.processorCore).append("\n")
                .append("19.HDD Type        : ").append(equipment.typeHDD).append("\n")
                .append("20.HDD number      : ").append(equipment.nbrHDD).append("\n")
                .append("21.HDD Capacity(Gb): ").append(equipment.capacityHDD).append("\n")
                .append("22.Screen size     : ").append(equipment.sceenSize).append("\n")
                .append("23.USB 2.0         : ").append(equipment.usb2).append("\n")
                .append("24.USB 3.0         : ").append(equipment.usb3).append("\n")
                .append("25.Hdmi            : ").append(equipment.hdmi).append("\n")
                .append("26.Vga             : ").append(equipment.vga).append("\n")
                .append("27.U.Battery(V)    : ").append(equipment.uBat).append("\n")
                .append("28.U.Adaptator(V)  : ").append(equipment.uAdapt).append("\n")
                .append("29.I.Adaptator(A)  : ").append(equipment.iAdapt).append("\n")
                .append("30.P.Adaptator(W)  : ").append(equipment.pAdapt).append("\n")
                .append("31.Key number      : ").append(equipment.numKey).append("\n")
                .append("32.Comment         : ").append(equipment.commentaire).append("\n")
                .append("33.Archive state   : ").append(equipment.archiver).append("\n")
                .append("-------------------------------------").append("\n")
                .append("34.Switch type     : ").append(equipment.typeSwitch).append("\n")
                .append("-------------------------------------").append("\n")
                .append("35.P.Printer(W)    : ").append(equipment.pImp).append("\n")
                .append("36.I.Printer(A)    : ").append(equipment.iImp).append("\n")
                .append("37.Page Per Min    : ").append(equipment.ppm).append("\n")
                .append("38.Printer type    : ").append(equipment.typeImp).append("\n")
                .append("39.U.Alimentation(V): ").append(equipment.uAlim).append("\n")
                .append("-------------------------------------").append("\n")
                .append("40.Number USB      : ").append(equipment.nbrUSB).append("\n")
                .append("41.Number memory   : ").append(equipment.nbrMemory).append("\n")
                .append("42.Number audio out: ").append(equipment.nbrAudio).append("\n")
                .append("43.Number micro.   : ").append(equipment.nbrMicro).append("\n")
                .append("44.Gain(dB)        : ").append(equipment.gain).append("\n")
                .append("45.Amplify type    : ").append(equipment.typeAmpl).append("\n")
                .append("-------------------------------------").append("\n")
                .append("46.Number Gbe      : ").append(equipment.gbe).append("\n")
                .append("47.Number Fe       : ").append(equipment.fe).append("\n")
                .append("48.Number Fo       : ").append(equipment.fo).append("\n")
                .append("49.Number serial   : ").append(equipment.serial).append("\n")
                .append("50.Number console  : ").append(equipment.console).append("\n")
                .append("51.Number aux.     : ").append(equipment.auxiliare).append("\n")
                .append("52.Default pwd.    : ").append(equipment.defaultPwd).append("\n")
                .append("53.Default IP Addr.: ").append(equipment.defaultIP).append("\n")
                .append("54.Support USB     : ").append(equipment.supportUSB).append("\n")
                .append("55.Router type     : ").append(equipment.typeRouter).append("\n")
                .append("56.IOS version     : ").append(equipment.versionIOS).append("\n")
                .append("-------------------------------------").append("\n")
                .append("57.AP. Range(m)    : ").append(equipment.range).append("\n")
                .append("58.AP. Type        : ").append(equipment.typeAP).append("\n")
                .append("-------------------------------------").append("\n")
                .append("59.Frequency range : ").append(equipment.frequencyRange).append("\n")
                .append("60.Number antenna  : ").append(equipment.nbrAntenna).append("\n")
                .append("-------------------------------------").append("\n")
                .append("61.Integrity(Netété): ").append(equipment.netete).append("\n")
                .append("62.Support Wifi    : ").append(equipment.supportWifi)
        }
        return stringReturn
    }

    override fun onResume() {
        super.onResume()

        //Set DefaultFragment value
        Factory.FRAGMENT_VALUE_TAG = FragmentTagValue.SEARCH
    }
}
