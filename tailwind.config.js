/** @type {import('tailwindcss').Config} */
module.exports = {
  // content: ["./src/main/resources/templates/posts/*{}"],
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {colors: {
        'regal-blue': '#243c5a',
      },},
  },
  plugins: [],
}

