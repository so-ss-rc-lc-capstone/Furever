/** @type {import('tailwindcss').Config} */
module.exports = {
  // content: ["./src/main/resources/templates/posts/*{}"],
  content: ["./src/**/*.{html,js}"],
  theme: {
    screens: {
      'xs': '360px',
      // => @media (min-width: 370px) { ... }

      'sm': '640px',
      // => @media (min-width: 640px) { ... }
    },
    extend: {colors: {
        'regal-blue': '#243c5a',
      },},
  },
  plugins: [],
}

