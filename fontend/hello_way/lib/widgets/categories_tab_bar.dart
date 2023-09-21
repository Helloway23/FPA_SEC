import 'package:flutter/material.dart';
import 'package:hello_way/res/app_colors.dart';
class CategoriesTabBar extends StatelessWidget {
  const CategoriesTabBar({
    Key? key,
    required this.onChanged,
    required this.selectedIndex,
    required this.items,
  }) : super(key: key);

  final ValueChanged<int> onChanged;
  final int selectedIndex;
  final List<String> items;

  @override
  Widget build(BuildContext context) {


    return Container(
      height: MediaQuery.of(context).size.height,
      color: Colors.white,
      child: Categories(
        onChanged: onChanged,
        selectedIndex: selectedIndex,
        items: items,
      ),
    );
  }
}

class Categories extends StatefulWidget {
  const Categories({
    Key? key,
    required this.onChanged,
    required this.selectedIndex,
    required this.items,
  }) : super(key: key);

  final ValueChanged<int> onChanged;
  final int selectedIndex;
  final List<String> items;

  @override
  State<Categories> createState() => _CategoriesState();
}

class _CategoriesState extends State<Categories> {
  late ScrollController _controller;

  @override
  void initState() {
    _controller = ScrollController();
    super.initState();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  void didUpdateWidget(covariant Categories oldWidget) {
    _controller.animateTo(
      80.0 * widget.selectedIndex,
      curve: Curves.ease,
      duration: const Duration(milliseconds: 200),
    );
    super.didUpdateWidget(oldWidget);
  }

  @override
  Widget build(BuildContext context) {
    final screenWidth = MediaQuery.of(context).size.width;
    return SingleChildScrollView(
      controller: _controller,
      scrollDirection: Axis.vertical,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,

        children: List.generate(
          widget.items.length,
              (index) => Container(

                width: 100,

            decoration: BoxDecoration(
              border: Border(

                left: BorderSide(
                  color: widget.selectedIndex == index
                      ? Colors.orange
                      : Colors.transparent,
                  width: 3.0,
                ),
                bottom: BorderSide(
                  color: lightGray,
                  width: 2.0,
                ),
              ),
            ),
            child: TextButton(
              onPressed: () {
                widget.onChanged(index);
              },
              style: TextButton.styleFrom(
                foregroundColor: widget.selectedIndex == index
                    ? Colors.orange
                    : Colors.black45,
              ),
              child: Text(
                widget.items[index]
                    .substring(0, 1)
                    .toUpperCase() +
                    widget.items[index].substring(1),
                style: const TextStyle(fontSize: 18),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
