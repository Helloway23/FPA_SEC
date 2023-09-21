import 'dart:convert';

import 'package:flutter/material.dart';

import '../models/space.dart';
import '../res/app_colors.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
class SpaceCard extends StatelessWidget {
  final Space space;
  const SpaceCard({Key? key, required this.space}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 5,
      child: Container(
        padding: const EdgeInsets.all(10),
        height: (MediaQuery.of(context).size.width / 2) - 20,
        width: (MediaQuery.of(context).size.width / 2) - 20,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(

                height: 120,
                width: MediaQuery.of(context).size.width,
                child: FittedBox(
                  fit: BoxFit.fill,
                  child:space.images?.length==0? Icon(Icons.image_outlined,color: gray,):Image.memory(base64.decode(space.images![0].data)),
                )),
            space.rating!=null?
            Padding(
                padding: EdgeInsets.only(
                  top: 5,
                ),
                child: Row(
                  children:  [
                    Icon(
                      Icons.star,
                      color: yellow,
                      size: 16,
                    ),
                    Text(
                    "${space.rating}/5 ",
                    ),
                    Text(
                    "("+space.numberOfRatings.toString()+")",
                      style: TextStyle(color: gray),
                    ),
                  ],
                )):
            Text(
              "Aucune note",  style: TextStyle(color: gray),
            ),
             Text(space.title)
          ],
        ),
      ),
    );
  }
}
