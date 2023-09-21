

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:hello_way_client/models/command.dart';

import '../res/app_colors.dart';
import '../view_models/commands_view_model.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
class ItemCommand extends StatefulWidget {
  final Command command;
  final double sum;
  const ItemCommand({
    super.key,
    required this.command,
    required this.sum,
  });

  @override
  State<ItemCommand> createState() => _ItemCommandState();
}

class _ItemCommandState extends State<ItemCommand> {
  late CommandsViewModel _listCommandsViewModel;

  @override
  void initState() {
    _listCommandsViewModel = CommandsViewModel(context);
    super.initState();
  }




  @override
  Widget build(BuildContext context) {

    return Container(
        padding: const EdgeInsets.only(left: 20, top: 10, bottom: 10),
        color: Colors.white,
        child: Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  Text(
                    "Commande  N°${widget.command.idCommand} ",
                    style: const TextStyle(
                        fontSize: 18,fontWeight: FontWeight.bold),
                  ),
                  SizedBox(width: 5,),
                  Container(
                    width: 15,
                    height: 15,
                    decoration: BoxDecoration(
                      shape: BoxShape.circle,
                      color:widget.command.status=="NOT_YET"? Colors.orangeAccent:Colors.green,
                    ),
                  ),
                ],
              ),

            ],
          ),
          SizedBox(height: 10,),
          if(widget.command.status=="CONFIRMED" || widget.command.status == "PAYED")
          Text("Total: ${widget.sum} DT"   ,style: const TextStyle(
              fontSize: 16)),

          SizedBox(height: 10,),
          Text(widget.command.localDate.toString(),style: const TextStyle(  color: gray))
        ]));
  }
}
