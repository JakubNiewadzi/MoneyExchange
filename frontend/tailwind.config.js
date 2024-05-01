/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  important: "#root",
  theme: {
    extend: {
      colors: {
        customBlue: '#02006c',
        background: '#010048',
        darkBlue: '#03002e',
        lightBlue: '#090088'
      },
    },
  },
  plugins: [],
}

