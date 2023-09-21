import 'dart:convert';
import 'package:flutter/material.dart';

import '../res/app_colors.dart';
import '../response/product_with_quantity.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
class BasketItem extends StatefulWidget {
  final ProductWithQuantities productWithQuantity;
  final void Function()?  onIncrement;
  final void Function()?  onDecrement;
  final void Function()? onDelete;

  const BasketItem({Key? key, required this.productWithQuantity, this.onDelete, this.onIncrement, this.onDecrement}) : super(key: key);

  @override
  _BasketItemState createState() => _BasketItemState();
}

class _BasketItemState extends State<BasketItem> {
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.white,
      padding: EdgeInsets.symmetric(horizontal: 20, vertical: 5),
      height: 130,
      width: MediaQuery.of(context).size.width,
      child: Column(
        children: [
          Row(
            children: [
              SizedBox(
                height: 120,
                width: 110,
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(5),
                  child: FittedBox(
                    fit: BoxFit.fill,
                    child: widget.productWithQuantity.product.images!.isEmpty
                        ? Icon(
                      Icons.image_outlined,
                      color: Colors.grey.withOpacity(0.5),
                    )
                        : Image.memory(
                        base64.decode(widget.productWithQuantity.product.images![widget.productWithQuantity.product.images!.length-1].data)),
                  ),
                ),
              ),
              Expanded(
                child: Padding(
                  padding:
                  const EdgeInsets.only(left: 10.0),
                  child: Column(

                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        widget.productWithQuantity.product.title.substring(0, 1).toUpperCase() +
                            widget.productWithQuantity.product.title.substring(1),
                        style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          fontSize: 18,
                        ),
                      ),
                      Text(
                        "${widget.productWithQuantity.product.price} DT",
                      ),
                      SizedBox(height: 20,),
                      Row(


                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [


                          Row(
                            children: [
                              InkWell(
                                onTap:
              widget.onDecrement,

                                child: Container(
                                  width: 25,
                                  height: 25,
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(5.0),
                                    border: Border.all(
                                      color: widget.productWithQuantity.quantity > 1 ? Colors.orange : Colors.grey, // Replace with your desired border color
                                      width: 1.0, // Replace with your desired border width
                                    ),
                                    color: Colors.white,
                                  ),
                                  child: Center(
                                    child: Icon(
                                      Icons.remove,
                                      color: widget.productWithQuantity.quantity > 1 ? Colors.orange : Colors.grey,
                                      size: 20.0,
                                    ),
                                  ),
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.symmetric(horizontal: 5),
                                child: Text(
                                  widget.productWithQuantity.quantity.toString(),
                                  style: TextStyle(fontSize: 18),
                                ),
                              ),
                              InkWell(
                                onTap: widget.onIncrement,
                                child: Container(
                                  width: 25,
                                  height: 25,
                                  decoration: BoxDecoration(
                                    borderRadius: BorderRadius.circular(5.0),
                                    border: Border.all(
                                      color: Colors.orange, // Replace with your desired border color
                                      width: 1.0, // Replace with your desired border width
                                    ),
                                  ),
                                  child: const Center(
                                    child: Icon(
                                      Icons.add,
                                      color: Colors.orange,
                                      size: 20.0,
                                    ),
                                  ),
                                ),
                              )
                            ],
                          ),
                          InkWell(
                            onTap:widget.onDelete,

                            child: Row(

                              // Adjust to control the width behavior
                              children: const [
                                Icon(
                                  Icons.delete, // Use the cart icon
                                  color: gray,
                                  size: 25,
                                ),
                                SizedBox(width: 5.0),
                                Text(
                                  'Retirer',
                                  style: TextStyle(
                                    color:gray,
                                    fontSize: 18,
                                  ),
                                ),
                              ],
                            ),
                          ),
                        ],
                      )
                    ],
                  ),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
