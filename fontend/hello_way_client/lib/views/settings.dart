import 'dart:io';

import 'package:flutter/material.dart';
import 'package:hello_way_client/utils/routes.dart';
import 'package:hello_way_client/utils/secure_storage.dart';
import 'package:provider/provider.dart';
import 'package:url_launcher/url_launcher.dart';
import '../l10n/l10n.dart';
import '../res/app_colors.dart';
import '../res/strings.dart';
import '../services/network_service.dart';
import '../utils/const.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

import '../view_models/language_provider.dart';

class Settings extends StatefulWidget {
  const Settings({Key? key}) : super(key: key);

  @override
  State<Settings> createState() => _SettingsState();
}

class _SettingsState extends State<Settings> {
  final SecureStorage secureStorage = SecureStorage();
  String? _userId;
  @override
  void initState() {
    authentifiedUser();
    super.initState();
  }

  Future<void> authentifiedUser() async {
   final userId = await secureStorage.readData(authentifiedUserId);

      setState(() {
        _userId=userId;
      });


  }

  void launchEmail() async {
    final email = "recipient@example.com"; // Replace with the recipient's email address

    final uri = Uri(
      scheme: "mailto",
      path: email,
    );

    if (await canLaunchUrl(uri)) {
      await launchUrl(uri);
    } else {
      throw 'Impossible de lancer l\'email';
    }
  }

  @override
  Widget build(BuildContext context) {
    final languageProvider = Provider.of<LanguageProvider>(context);
    NetworkStatus networkStatus = Provider.of<NetworkStatus>(context);
    return Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          automaticallyImplyLeading: false,
          backgroundColor: orange,
          title:  Text(
            AppLocalizations.of(context)!.settings,
            style: const TextStyle(color: Colors.white),
          ),
        ),
        body:networkStatus == NetworkStatus.Online
            ? SingleChildScrollView(
            child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  _userId!=null?const SizedBox() : Container(
                color: lightGray,
                width: MediaQuery.of(context).size.width,
                padding: const EdgeInsets.all(20),
                child:  Text(
                  AppLocalizations.of(context)!.connexion,
                  style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                ),
              ),
                  _userId!=null?SizedBox() :Container(
                padding: const EdgeInsets.only(top: 5, bottom: 5),
                width: MediaQuery.of(context).size.width,
                color: Colors.white,
                child: ListTile(
                  leading: const Icon(Icons.person_outline_outlined),
                  contentPadding: const EdgeInsets.symmetric(horizontal: 20),
                  visualDensity: const VisualDensity(vertical: 0.0),
                  dense: true,
                  title:  Text(AppLocalizations.of(context)!.authentication,
                      style: const TextStyle(fontSize: 16)),
                  onTap: () {
                    Navigator.pushNamed(context, loginRoute, arguments: 4);
                  },
                ),
              ),


      Container(
          color: lightGray,
          width: MediaQuery.of(context).size.width,
          padding: const EdgeInsets.all(20),
                    child: Text(
                      AppLocalizations.of(context)!.general,
                      style: const TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 16,
                      ),
                    ),
                  ),
                  Container(
                    padding: const EdgeInsets.only(top: 5, bottom: 5),
                    width: MediaQuery.of(context).size.width,
                    color: Colors.white,
                    child: Column(
                      children: [
                        ListTile(
                          leading: const Icon(Icons.language_rounded),
                          trailing: const Icon(Icons.navigate_next_rounded),
                          visualDensity: const VisualDensity(vertical: 0.0),
                          dense: true,
                          title:  Text(
                            AppLocalizations.of(context)!.language,
                            style: const TextStyle(fontSize: 16),
                          ),
                          onTap: () {
                            showDialog(
                              context: context,
                              builder: (context) {
                                return AlertDialog(
                                  title: Text( AppLocalizations.of(context)!.language),
                                  content: StatefulBuilder(
                                    builder: (context, setState) {
                                      return Column(
                                        mainAxisSize: MainAxisSize.min,
                                        children: L10n.all.map((locale) {
                                          int index = languageProvider
                                              .getCurrentLanguageIndex();
                                          bool isSelected = locale == L10n.all[index];
                                          return RadioListTile(
                                            title:
                                            Text(locale.languageCode.toUpperCase()),
                                            value: locale,
                                            groupValue:
                                            isSelected ? L10n.all[index] : null,
                                            onChanged: (value) {
                                              languageProvider.changeLocale(value!);
                                              Navigator.pop(context);
                                            },
                                          );
                                        }).toList(),
                                      );
                                    },
                                  ),
                                );
                              },
                            );
                          },
                        ),
                        const Divider(),
                        ListTile(
                          leading: const Icon(Icons.message),
                          trailing: const Icon(Icons.navigate_next_rounded),
                          dense: true,
                          title: Text(
                            AppLocalizations.of(context)!.contactUs,
                            style: const TextStyle(fontSize: 16),
                          ),
                          onTap: () {
                            launchEmail();
                          },
                        ),
                        const Divider(),
                        ListTile(
                          leading: const Icon(Icons.info_sharp),
                          trailing: const Icon(Icons.navigate_next_rounded),
                          visualDensity: const VisualDensity(vertical: 0.0),
                          dense: true,
                          title:  Text(
                            AppLocalizations.of(context)!.aboutUs,
                            style: const TextStyle(fontSize: 16),
                          ),
                          onTap: () {
                            //    Navigator.pushNamed(context, signUpRoute);
                          },
                        ),
                      ],
                    ),
                  ),
              Container(
                color: lightGray,
                width: MediaQuery.of(context).size.width,
                padding: const EdgeInsets.all(20),
                child: Text(
                  AppLocalizations.of(context)!.help,
                  style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    fontSize: 16,
                  ),
                ),
              ),
              Container(
                padding: const EdgeInsets.only(top: 5, bottom: 5),
                width: MediaQuery.of(context).size.width,
                child: Column(
                  children: [
                    ListTile(
                      leading: const Icon(Icons.help_outline_outlined),
                      contentPadding:
                          const EdgeInsets.symmetric(horizontal: 20),
                      visualDensity: const VisualDensity(vertical: 0.0),
                      dense: true,
                      title:  Text(
                        AppLocalizations.of(context)!.userManual,
                        style: const TextStyle(fontSize: 16),
                      ),
                      onTap: () {
                        //    Navigator.pushNamed(context, signUpRoute);
                      },
                    ),
                    ListTile(
                      leading: const Icon(Icons.chat_outlined),
                      contentPadding:
                          const EdgeInsets.symmetric(horizontal: 20),
                      visualDensity: const VisualDensity(vertical: 0.0),
                      dense: true,
                      title:  Text(
                        AppLocalizations.of(context)!.fqa,
                        style: const TextStyle(fontSize: 16),
                      ),
                      onTap: () {
                        //    Navigator.pushNamed(context, signUpRoute);
                      },
                    ),

                  ],
                ),
              ),

            ])):Center(
          child: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Icon(
                  Icons.network_check,
                  size: 150,
                  color: gray,
                ),
                const SizedBox(height: 20),
                Text(
                  AppLocalizations.of(context)!.noInternet,
                  style: const TextStyle(fontSize: 22, color: gray),
                  textAlign: TextAlign.center,
                ),
                Text(
                  AppLocalizations.of(context)!.checkYourInternet,
                  style: const TextStyle(fontSize: 22, color: gray),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: 10,),
                MaterialButton(
                  color: orange,
                  height: 40,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(10.0),
                  ),
                  onPressed:(){
                    setState(() {

                    });
                  },


                  child: Text(
                    AppLocalizations.of(context)!.retry,
                    style: const TextStyle(
                        fontSize: 20,
                        color: Colors.white,
                        fontWeight: FontWeight.bold),
                  ),

                )
              ],
            ),
          ),
        ));
  }
}
