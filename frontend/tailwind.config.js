/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./src/**/*.{js,jsx,ts,tsx}",
    ],
    important: "#root",
    theme: {
        fontFamily: {
            sans: ['Arial'],
        },
        extend: {
            colors: {
                background: '#18191a',
                darkGray: '#242526',
                lightGray: '#3a3b3c'
            },
        },
    },
    plugins: [],
};

