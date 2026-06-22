import axios from "axios";


export const sendPushNotification = async (
    playerId,
    title,
    message
) => {

    try {

        const response = await axios.post(
            "https://onesignal.com/api/v1/notifications",
            {
                app_id: process.env.ONESIGNAL_APP_ID,

                include_player_ids: [
                    playerId
                ],

                headings: {
                    en: title
                },

                contents: {
                    en: message
                }
            },
            {
                headers: {
                    Authorization:
                    `Basic ${process.env.ONESIGNAL_API_KEY}`
                }
            }
        );


        return response.data;


    } catch(error){

        console.error(
            "Error enviando notificación:",
            error.response?.data || error.message
        );

        throw error;

    }

};