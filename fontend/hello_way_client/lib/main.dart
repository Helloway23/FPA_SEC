import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:hello_way_client/res/app_colors.dart';
import 'package:hello_way_client/services/network_service.dart';
import 'package:hello_way_client/services/push_notification_service.dart';
import 'package:hello_way_client/utils/routes.dart';
import 'package:hello_way_client/view_models/language_provider.dart';

import 'package:hello_way_client/views/basket.dart';
import 'package:hello_way_client/views/camera_screen.dart';

import 'package:hello_way_client/views/change_password.dart';
import 'package:hello_way_client/views/forget_password.dart';
import 'package:hello_way_client/views/list.dart';
import 'package:hello_way_client/views/list_commands.dart';
import 'package:hello_way_client/views/login.dart';
import 'package:hello_way_client/views/menu.dart';
import 'package:hello_way_client/views/my_account.dart';
import 'package:hello_way_client/views/product_details.dart';
import 'package:hello_way_client/views/profile.dart';

import 'package:hello_way_client/views/settings.dart';
import 'package:hello_way_client/views/signup.dart';
import 'package:hello_way_client/views/space_location.dart';

import 'package:provider/provider.dart';
import 'l10n/l10n.dart';
import 'navigations/bottom_navigation_bar_with_fab.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';


void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // Initialize the notification plugin
  await _initializeNotifications();


  runApp(MyApp());
}

Future<void> _initializeNotifications() async {

  final FlutterLocalNotificationsPlugin flutterLocalNotificationsPlugin =
  FlutterLocalNotificationsPlugin();
  const AndroidInitializationSettings initializationSettingsAndroid =
  AndroidInitializationSettings('app_icon'); // Replace 'app_icon' with your app icon name in the drawable folder.

  final InitializationSettings initializationSettings = InitializationSettings(
    android: initializationSettingsAndroid,
  );

  await flutterLocalNotificationsPlugin.initialize(
    initializationSettings,
  );
}


class MyApp extends StatelessWidget {
  // Create an instance of FlutterLocalNotificationsPlugin

  final Locale initialLocale;
  late Locale _locale;

  MyApp({Key? key, this.initialLocale = const Locale('en')})
      : super(key: key) {
    _locale = initialLocale;
  }

  void updateLocale(Locale newLocale) {
    _locale = newLocale;
  }


  @override
  Widget build(BuildContext context) {
    final pushNotificationService = PushNotificationService(
      flutterLocalNotificationsPlugin: FlutterLocalNotificationsPlugin(),
      context: context, // Provide the context from your widget tree
    );

    pushNotificationService.init();
    return
      MultiProvider(
        providers: [
        ChangeNotifierProvider.value(value: pushNotificationService),
    ChangeNotifierProvider.value(value: LanguageProvider()),
          StreamProvider<NetworkStatus>(
            create: (_) => NetworkStatusService().networkStatusController.stream,
            initialData: NetworkStatus.Online,
          ),
    ],
    child:Consumer<LanguageProvider>(
    builder: (_, languageProvider, __) {
      return MaterialApp(
      theme: ThemeData().copyWith(
        colorScheme: ThemeData().colorScheme.copyWith(
          primary:orange,

        ),
      ),
      supportedLocales: L10n.all,
      localizationsDelegates: [
        AppLocalizations.delegate,

        GlobalMaterialLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      locale: languageProvider.locale,
    title: 'Flutter Demo',
    routes: {
     splashScreenRoute: (context) {
        return  BottomNavigationBarWithFAB();
      },
      loginRoute: (context) {
        return const Login();
      },
      signUpRoute: (context) {
        return const SignUp();
      },
      bottomNavigationWithFABRoute: (context) {
        return const BottomNavigationBarWithFAB();
      },
      settingsRoute: (context) {
        return const Settings();
      },
      forgetPasswordRoute: (context) {
        return  const ForgetPassword();
      },

      changePasswordRoute : (context) {
        return  const ChangePassword();
      },
      myAccountRoute: (context) {
        return  const MyAccount();
      },
      profileRoute: (context) {
        return  const Profile();
      },
      productDetailsRoute: (context) {
        return  const ProductDetails();
      },
      flottingActionButtonMenuRoute: (context) {
        return   Spacer();
      },

      menuRoute: (context) {
        return    Menu();
      },
      cameraScreenRoute: (context) {
        return    CameraScreen();
      },
      basketRoute: (context) {
        return    Basket();
      },
      listCommandsRoute: (context) {
        return    ListCommands();
      },
      listReservationsRoute: (context) {
        return    ListReservations();
      },
      spaceLocationRoute: (context) {
        return SpaceLocation();
      },

    });
    }));
  }
}
