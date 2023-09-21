import 'package:flutter/material.dart';
import 'package:shimmer/shimmer.dart';

import '../res/app_colors.dart';

class ItemCommandShimmer extends StatelessWidget {
  const ItemCommandShimmer({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      padding: const EdgeInsets.all(10),
      child: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              Shimmer.fromColors(
                baseColor: gray.withOpacity(0.5),
                highlightColor: Colors.white,
                child: Container(
                  height: 15,
                  width: MediaQuery.of(context).size.width/2,
                  decoration: BoxDecoration(
                    color: Colors.white,
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
              ),


              Shimmer.fromColors(
                baseColor: gray.withOpacity(0.5),
                highlightColor: Colors.white,
                child: Container(
                  width: 15,
                  height: 15,
                  decoration: const BoxDecoration(
                    shape: BoxShape.circle,
                    color:Colors.white,
                  ),
                ),
              ),
            ],
          ),
          const SizedBox(
            height: 10,
          ),
          Shimmer.fromColors(
            baseColor: gray.withOpacity(0.5),
            highlightColor: Colors.white,
            child: Container(
              height: 10,
              width:  MediaQuery.of(context).size.width / 3,
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),
          const SizedBox(height: 10,),
          Shimmer.fromColors(
            baseColor: gray.withOpacity(0.5),
            highlightColor: Colors.white,
            child: Container(
              height: 10,
              width:  MediaQuery.of(context).size.width / 2,
              decoration: BoxDecoration(
                color: Colors.white,
                borderRadius: BorderRadius.circular(8),
              ),
            ),
          ),

        ],
      ),
    );
  }
}
