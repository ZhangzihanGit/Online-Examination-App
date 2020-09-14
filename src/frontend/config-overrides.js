const { override, addLessLoader, fixBabelImports } = require('customize-cra');

module.exports = override(
  // import styles as needed
  fixBabelImports('antd', {
    libraryDirectory: 'es',
    style: 'css'
  }),

  // transform CSS to Less
  addLessLoader({
    lessOptions: {
      javascriptEnabled: true,
    }
  })
)