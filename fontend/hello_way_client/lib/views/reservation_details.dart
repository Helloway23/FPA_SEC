import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../res/app_colors.dart';

import '../Widgets/button.dart';
import '../models/reservation.dart';
import '../services/network_service.dart';
import '../view_models/reservations_view_model.dart';
import '../widgets/snack_bar.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
class ReservationDetails extends StatefulWidget {
  final Reservation reservation;

  const ReservationDetails({
    super.key,
    required this.reservation,
  });

  @override
  State<ReservationDetails> createState() => _ReservationDetailsState();
}

class _ReservationDetailsState extends State<ReservationDetails> {
  late ReservationsViewModel _reservationsViewModel;
  final GlobalKey<ScaffoldMessengerState> _reservationDetailsScaffoldKey =
      GlobalKey<ScaffoldMessengerState>();


  @override
  void initState() {
    _reservationsViewModel=ReservationsViewModel(context);
    super.initState();
  }


  @override
  Widget build(BuildContext context) {
    NetworkStatus networkStatus = Provider.of<NetworkStatus>(context);
    return ScaffoldMessenger(
        key: _reservationDetailsScaffoldKey,
        child: Scaffold(
          appBar: AppBar(
            backgroundColor: orange,
            title: Text(
              "${AppLocalizations.of(context)!.reservation} N°${widget.reservation.idReservation}",
              style: const TextStyle(color: Colors.white),
            ),
          ),
          body:networkStatus == NetworkStatus.Online
              ? Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Expanded(
                child: SingleChildScrollView(
                  child: Container(
                    padding: const EdgeInsets.all(20),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(
                              "${AppLocalizations.of(context)!.reservation} N°${widget.reservation.idReservation}",
                              style: const TextStyle(
                                  fontSize: 18, fontWeight: FontWeight.bold),
                            ),
                            Container(
                              padding: const EdgeInsets.all(5),
                              decoration: BoxDecoration(
                                color: widget.reservation.status == "NOT_YET"
                                    ? Colors.orangeAccent
                                    : widget.reservation.status == "CONFIRMED"
                                        ? Colors.green
                                        : Colors.red,
                                borderRadius: BorderRadius.circular(5),
                              ),
                              child: Text(
                                widget.reservation.status == "NOT_YET"
                                    ? AppLocalizations.of(context)!.pendingStatus
                                    : widget.reservation.status == "REFUSED"
                                        ? AppLocalizations.of(context)!.refusedStatus
                                        : widget.reservation.status == "CONFIRMED"
                                            ? AppLocalizations.of(context)!.confirmedStatus
                                            : AppLocalizations.of(context)!.canceledStatus,
                                style: const TextStyle(color: Colors.white),
                              ),
                            )
                          ],
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        Text(
                            widget.reservation.eventTitle
                                    .substring(0, 1)
                                    .toUpperCase() +
                                widget.reservation.eventTitle.substring(1),
                            style: const TextStyle(fontSize: 16)),
                        const SizedBox(
                          height: 10,
                        ),
                        Text(widget.reservation.startDate.toString(),
                            style: const TextStyle(color: gray, fontSize: 16)),
                        const SizedBox(
                          height: 10,
                        ),
                        Container(
                          color: lightGray,
                          height: 10,
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                        RichText(
                          text: TextSpan(
                            children: <TextSpan>[
                               TextSpan(
                                text: AppLocalizations.of(context)!.numberOfGuests,
                                style: const TextStyle(
                                    fontSize: 16,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.black),
                              ),
                              TextSpan(
                                text:
                                    " ${widget.reservation.numberOfGuests} ${AppLocalizations.of(context)!.persons}",
                                style: const TextStyle(
                                    fontSize: 16, color: Colors.black),
                              ),
                            ],
                          ),
                        ),
                        const SizedBox(
                          height: 10,
                        ),
                         Text("${AppLocalizations.of(context)!.description}:",
                            style: const TextStyle(
                                fontSize: 16, fontWeight: FontWeight.bold)),
                        Text(   widget.reservation.description!.substring(0, 1)
                            .toUpperCase() +
                            widget.reservation.description!.substring(1),
                            style: const TextStyle(
                                fontSize: 16)),
                        const SizedBox(
                          height: 10,
                        ),

                        if(widget.reservation.status == "CONFIRMED")
                         Text("${AppLocalizations.of(context)!.tables}:",
                            style: const TextStyle(
                                fontSize: 16, fontWeight: FontWeight.bold)),
                        const SizedBox(
                          height: 10,
                        ),


                      ],
                    ),
                  ),
                ),
              ),
                Align(
                  alignment: Alignment.bottomCenter,
                  child: Container(
                      color: Colors.white,
                      padding: const EdgeInsets.all(16),
                      child: Container(
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(5.0),
                          color: orange,
                        ),
                        child: Button(
                          onPressed: () {
                            _reservationsViewModel
                                .cancelReservation(widget
                                .reservation.idReservation!)
                                .then((reservation) async {

                              if(reservation!=null){
                                setState(() {
                                  widget.reservation.status =
                                  "CANCELED";
                                });
                                var snackBar =  customSnackBar(context, AppLocalizations.of(context)!.reservationCancelled, Colors.green);
                                _reservationDetailsScaffoldKey.currentState?.showSnackBar(snackBar);
                              }

                            }).catchError((error) {});

                          },
                          text: AppLocalizations.of(context)!.cancelReservation,

                        ),
                      )),
                ),
            ],
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
        ));
  }
}
