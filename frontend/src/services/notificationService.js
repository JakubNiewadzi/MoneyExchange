import {formatDate, isValidDate} from "./dateService";

export const handleNotificationString = (notificationString) => {
    notificationString = notificationString.replaceAll('"', '');

    let dateString = notificationString.slice(-16);
    if (isValidDate(dateString)){
        dateString = formatDate(dateString);
        notificationString = notificationString.slice(0, -16) + dateString;
    }

    return notificationString;
};

