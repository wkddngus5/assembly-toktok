const webpack = require('webpack');
const path = require("path");

const CommonsChunkPlugin = webpack.optimize.splitChunks;

module.exports = {
  entry: {
    whatwg: 'whatwg-fetch',
    index: "./src/main/resources/public/entry/main.js",
    projectList: './src/main/resources/public/entry/projectList.js'
  },
  output: {
    path: path.resolve(__dirname, "src/main/resources/public/dist/"),
    publicPath: "/dist/",
    filename: "[name]-bundle.js"
  },
  module: {
    rules: [{
      test: /\.css$/,
      exclude: /node_modules/,
      loaders: ['style-loader', 'css-loader'],
    },
      {
        test: /\.js$/,
        exclude: /node_modules/,
        include: path.join(__dirname, 'src'),
        use: [{
          loader: "babel-loader",
          query: {
            presets: ['es2015', 'react']
          }
        }
        ]
      },
      {
        test: /\.(pdf|jpg|png|gif|svg|ico)$/,
        use: [
          {
            loader: 'url-loader'
          },
        ]
      }
    ]
  }
};