// const responseInterceptors = [
//   {
//     name: 'formatResponse',
//     // Any status code that lie within the range of 2xx cause this function to trigger
//     // Do something with response data
//     success(response) {
//       // console.log(response);
//       // return response.data;
//       return response;
//     },
//     // Any status codes that falls outside the range of 2xx cause this function to trigger
//     // Do something with response error
//     fail(error) {
//       return Promise.reject(error);
//     }
//   }
// ];

// const requestInterceptors = [];

// const interceptors = {
//   response: responseInterceptors,
//   request: requestInterceptors,
// };

// // install interceptors to axios instances
// function doInstall(instance, option = {}) {
//   const { type } = option;
//   interceptors[type]
//     .forEach(interceptor => {
//       const { success, fail } = interceptor;
//       instance.interceptors[type].use(success, fail);
//     })
// }

// export function install(instance, option = {}) {
//   doInstall(instance, {
//     type: 'request',
//   });
//   doInstall(instance, {
//     type: 'response',
//   });
// }; 