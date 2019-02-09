select materiel.id as idInt,materiel.code_str as idStr,categorie_materiel.designation as categorieMateriel,date_acquisition as acquisitionDate,garantie.valeur as guaranty,marque.designation as marque,
modele.designation as model,couleur.designation as couleur,poids.valeur as weight,etat_materiel.designation as etat,materiel.label as label,
materiel.mac_adresse1 as macWIFI, materiel.mac_adresse2 as macLAN,type_ordinateur.designation as typeComputer,type_clavier.designation as typeKeybord,OS.designation as operatingS,ram.id as ram,processeur.valeur as processeur,
nombre_coeur_processeur.valeur as processorCore,type_hdd.designation as typeHDD,nombre_hdd.valeur as nbrHDD,capacite_hdd.valeur as capacityHDD,taille_ecran.valeur as sceenSize,usb2.valeur as usb2,usb3.valeur as usb3,hdmi.valeur as hdmi,
vga.valeur as vga,tension_adaptateur.valeur as uBat,tension_adaptateur.valeur as uAdapt,puissance_adaptateur.valeur as pAdapt,materiel.numero_cle as numKey, intensite_adaptateur.valeur as iAdapt, 
materiel.commentaire as commentaire,materiel.archiver as archiver,

type_switch.designation as typeSwitch,

puissance.valeur as pImp,intensite.valeur as iImp,page_par_minute.valeur as ppm,type_imprimante.designation as typeImp,

tension_alimentation.valeur as uAlim,usb.valeur as nbrUSB,memoire.valeur as nbrMemory,sorties_audio.valeur as nbrAudio,microphone.valeur as nbrMicro,gain.valeur as gain,type_amplificateur.designation as typeAmpl,

gbe.valeur as gbe,fe.valeur as fe,fo.valeur as fo,serial.valeur as serial,default_pwd.designation as defaultPwd,default_ip.designation as defaultIP,console.valeur as console,auxiliaire.valeur as auxiliare,materiel.capable_usb as supportUSB, type_routeur_AP.designation as typeRouter, version_ios.designation as versionIOS,

portee.valeur as range,type_AP.designation as typeAP,

frequence.designation as frequencyRange,antenne.valeur as nbrAntenna,

netette.designation as netete,materiel.compatible_wifi as supportWifi

from materiel 
left outer join garantie on garantie.id=materiel.id_garantie
left outer join categorie_materiel on categorie_materiel.id=materiel.id_categorie_materiel
inner join marque on marque.id=materiel.id_marque
inner join modele on modele.id=materiel.id_modele
inner join couleur on couleur.id=materiel.id_couleur
inner join poids on poids.id=materiel.id_poids
inner join etat_materiel on etat_materiel.id=materiel.id_etat_materiel
left outer join type_ordinateur on type_ordinateur.id=materiel.id_type_ordinateur
left outer join type_clavier on type_clavier.id=materiel.id_type_clavier
left outer join OS on OS.id=materiel.id_OS
left outer join ram on ram.id=materiel.id_ram
left outer join processeur on processeur.id=materiel.id_processeur
left outer join nombre_coeur_processeur on nombre_coeur_processeur.id=materiel.id_nombre_coeur_processeur
left outer join type_hdd on type_hdd.id=materiel.id_type_hdd
left outer join nombre_hdd on nombre_hdd.id=materiel.id_nombre_hdd
left outer join capacite_hdd on capacite_hdd.id=materiel.id_capacite_hdd
left outer join taille_ecran on taille_ecran.id=materiel.id_taille_ecran
left outer join usb2 on usb2.id=materiel.id_usb2
left outer join usb3 on usb3.id=materiel.id_usb3
left outer join hdmi on hdmi.id=materiel.id_hdmi
left outer join vga on vga.id=materiel.id_vga
left outer join tension_batterie on tension_batterie.id=materiel.id_tension_batterie
left outer join tension_adaptateur on tension_adaptateur.id=materiel.id_tension_adaptateur
left outer join puissance_adaptateur on puissance_adaptateur.id=materiel.id_puissance_adaptateur
left outer join intensite_adaptateur on intensite_adaptateur.id=materiel.id_intensite_adaptateur

--Printer
left outer join type_imprimante on type_imprimante.id=materiel.id_type_imprimante
left outer join puissance on puissance.id=materiel.id_puissance
left outer join intensite on intensite.id=materiel.id_intensite
left outer join page_par_minute on page_par_minute.id=materiel.id_page_par_minute

--Amplificateur
left outer join type_amplificateur on type_amplificateur.id=materiel.id_type_amplificateur
left outer join tension_alimentation on tension_alimentation.id=materiel.id_tension_alimentation
left outer join usb on usb.id=materiel.id_usb
left outer join memoire on memoire.id=materiel.id_memoire
left outer join sorties_audio on sorties_audio.id=materiel.id_sorties_audio
left outer join microphone on microphone.id=materiel.id_microphone
left outer join gain on gain.id=materiel.id_gain

--Routeur_AP
left outer join type_routeur_AP on type_routeur_AP.id=materiel.id_type_routeur_AP
left outer join gbe on gbe.id=materiel.id_gbe
left outer join fe on fe.id=materiel.id_fe
left outer join fo on fo.id=materiel.id_fo
left outer join serial on serial.id=materiel.id_serial
left outer join default_pwd on default_pwd.id=materiel.id_default_pwd
left outer join default_ip on default_ip.id=materiel.id_default_ip
left outer join console on console.id=materiel.id_console
left outer join auxiliaire on auxiliaire.id=materiel.id_auxiliaire
left outer join version_ios on version_ios.id=materiel.id_version_ios
--capable_usb

--AccessPoint
left outer join type_AP on type_AP.id=materiel.id_type_AP
left outer join portee on portee.id=materiel.id_portee

--Switch
left outer join type_switch on type_switch.id=materiel.id_type_switch

--Emetteur
left outer join frequence on frequence.id=materiel.id_frequence
left outer join antenne on antenne.id=materiel.id_antenne

--Retroprojecteur
left outer join netette on netette.id=materiel.id_netette
where  materiel.code_str='2-1' 
