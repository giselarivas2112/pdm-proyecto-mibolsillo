import supabase from "../config/supabase.js";
import { sendPushNotification }
    from "../services/notification_service.js";


export const checkFixedPayments = async () => {

    /*
    const today = new Date(
        new Date().toLocaleString(
            "en-US",
            {
                timeZone: "America/El_Salvador"
            }
        )
    );
    */

    const today = new Date(2026, 5, 30);
    const currentDay = today.getDate();

    const { data: payments, error } = await supabase
        .from("pagos_fijos")
        .select(`
            *,
            usuarios(
                onesignal_id
            )
        `);



    if (error) {

        console.error(
            "Error buscando pagos:",
            error
        );

        return;

    }



    for (const payment of payments) {


        


        let daysUntilPayment;

        if (payment.dia_vencimiento >= currentDay) {

            daysUntilPayment =
                payment.dia_vencimiento - currentDay;

        } else {

            const daysInMonth =
                new Date(
                    today.getFullYear(),
                    today.getMonth() + 1,
                    0
                ).getDate();

            daysUntilPayment =
                (daysInMonth - currentDay)
                + payment.dia_vencimiento;
        }

       

        if (daysUntilPayment === payment.dias_recordatorio) {


            const playerId =
                payment.usuarios?.onesignal_id;



            if (playerId) {


                await sendPushNotification(
                    playerId,
                    "Pago próximo",
                    `Tu pago "${payment.nombre}" vence en ${daysUntilPayment} días`
                );


                console.log(
                    "Notificación enviada a:",
                    payment.nombre
                );


            } else {

                console.log(
                    "Usuario sin OneSignal ID:",
                    payment.nombre
                );

            }


        }


    }


};