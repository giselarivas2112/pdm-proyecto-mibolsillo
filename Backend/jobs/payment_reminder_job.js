import { supabase } from "../config/supabase.js";
import { sendPushNotification } 
from "../services/notification_service.js";


export const checkFixedPayments = async () => {


    const today = new Date();


    const { data: payments, error } = await supabase
        .from("pagos_fijos")
        .select(`
            *,
            usuarios(
                onesignal_id
            )
        `);



    if(error){

        console.error(
            "Error buscando pagos:",
            error
        );

        return;

    }



    for(const payment of payments){


        const currentDay = today.getDate();



        const daysUntilPayment =
        payment.dia_vencimiento - currentDay;



        if(daysUntilPayment === payment.dias_recordatorio){



            const playerId =
            payment.usuarios?.onesignal_id;



            if(playerId){


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