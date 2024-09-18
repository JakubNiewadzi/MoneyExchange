export const formatDate = (dateString) => {
    const date = new Date(dateString);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${year}-${month}-${day} ${hours}:${minutes}`;
};

export const requestDate = (dateString) => {
    return `${formatDate(dateString)}:00`;
};

export const hasHourPassed = (dateString) => {

    const date = new Date(dateString);
    const now = new Date();

    const diffInMilliseconds = now - date;

    const diffInHours = diffInMilliseconds / (1000 * 60 * 60);

    return diffInHours >= 1;

};