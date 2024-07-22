// Karma configuration
// Generated on Sun Jul 21 2024 19:16:35 GMT-0500 (hora de Ecuador)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://www.npmjs.com/search?q=keywords:karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      
      'src/app/components/student/student.component.spec.ts',
      'src/app/components/course/course.component.spec.ts'
      
    ],

    exclude: [
    ],
    preprocessors: {},
    reporters: ['progress'],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ['Chrome'],
    singleRun: false,
    concurrency: Infinity,
  })
}
