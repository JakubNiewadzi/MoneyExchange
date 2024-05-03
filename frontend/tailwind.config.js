/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  important: "#root",
  theme: {
    extend: {
      colors: {
        customBlue: '#005b96',
        background: '#03396c',
        darkBlue: '#011f4b',
        lightBlue: '#6497b1'
      },
    },
  },
  plugins: [],
}

