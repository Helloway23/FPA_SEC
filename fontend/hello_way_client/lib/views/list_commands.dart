import 'package:flutter/material.dart';
import 'package:hello_way_client/models/command.dart';
import 'package:hello_way_client/res/app_colors.dart';
import 'package:hello_way_client/shimmer/item_command_shimmer.dart';
import 'package:provider/provider.dart';
import '../../widgets/item_command.dart';
import '../services/network_service.dart';
import '../view_models/commands_view_model.dart';
import 'command_details.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
class ListCommands extends StatefulWidget {
  const ListCommands({super.key});

  @override
  State<ListCommands> createState() => _ListCommandsState();
}

class _ListCommandsState extends State<ListCommands> {
  late CommandsViewModel _listCommandsViewModel ;
  int selectedStatusIndex = 0;

  Future<List<Command>?> getCommandsByUserId() async {
    List<Command>? commands =
        await _listCommandsViewModel.getCommandsByUserId();
    return commands;
  }

  Future<double> getSumOfCommand(int commandId) async {
    double sum = await _listCommandsViewModel.getSumOfCommand(commandId);
    return sum;
  }
  @override
  void initState() {
    // TODO: implement initState
    _listCommandsViewModel = CommandsViewModel(context);
    getCommandsByUserId( );

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    NetworkStatus networkStatus = Provider.of<NetworkStatus>(context);
    return Scaffold(
      backgroundColor: lightGray,
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text(AppLocalizations.of(context)!.myOrders),
      ),
      body:networkStatus == NetworkStatus.Online
          ? FutureBuilder(
        future: getCommandsByUserId(),
        builder: (BuildContext context,
            AsyncSnapshot snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return ListView.separated(
              itemCount: 10,
              separatorBuilder: (context, index) =>  SizedBox(height: 10),
              itemBuilder: (context, index) {
                return  const ItemCommandShimmer();
              },
            );
          } else if (snapshot.hasError) {
            return  Center(
              child: Text(AppLocalizations.of(context)!.errorRetrievingData),
            );
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center();
          } else {
            final commands = snapshot.data!;
            return ListView.separated(
              itemCount: commands.length,
              itemBuilder: (context, index) {
                Command command = commands[index];
                return FutureBuilder(
                  future: getSumOfCommand(
                      command.idCommand),
                  builder: (BuildContext context,
                      AsyncSnapshot<double> sumSnapshot) {
                    if (sumSnapshot.connectionState ==
                        ConnectionState.waiting) {
                      return const SizedBox
                          .shrink(); // or a loading widget
                    } else if (sumSnapshot.hasError) {
                      return const Text('Error retrieving sum');
                    } else {
                      final sum = sumSnapshot.data!;
                      return GestureDetector(
                        child: ItemCommand(
                          command: command,
                          sum: sum,

                        ),
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => CommandDetails(
                                command: command,
                                sum: sum,
                              ),
                            ),
                          );
                        },
                      );
                    }
                  },
                );
              },
              separatorBuilder: (context, index) {
                return Container(
                  color: lightGray,
                  height: 10,
                );
              },
            );
          }
        },
      ):Center(
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
      ),
    );
  }
}
