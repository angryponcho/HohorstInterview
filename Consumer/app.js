const stompit = require('stompit');
const mysql = require('mysql');

//local connection to apache activeMQ 
const connectOptions = {
  'host': 'localhost',
  'port': 61613,
  'connectHeaders':{
    'host': '/',
    'login': 'admin',
    'passcode': 'admin'
  }
};

//stompit library makes this much simpler from node's side
stompit.connect(connectOptions, function(error, client) {
  
    if (error) {
      console.log('connect error ' + error.message);
      return;
    }
    
    //must prepend /queue to the queue name for stompit
    const subscribeHeaders = {
      'destination': '/queue/BookQueue'
    };
    

    //we'll listen on this queue and write in any messages we spot
    client.subscribe(subscribeHeaders, function(error, message) {
      
      if (error) {
        console.log('subscribe error ' + error.message);
        return;
      }
      
      //got a message? do this:
      message.readString('utf-8', function(error, body) {
        
        if (error) {
          console.log('read message error ' + error.message);
          return;
        }
        
        console.log('received message: ' + body);

        //break the message up 
        let book = JSON.parse(body);
        var userId = book.userId;
        var title = book.title;
        var body = book.body;
        
        //input it into MySQL db
        var con = mysql.createConnection({
            host: "localhost",
            user: "root",
            password: "password",
            database: "ellucian"
        });
        
        con.connect(function(err) {
            if (err) throw err;
            console.log("Connected to MySQL!");

            //ES6 template strings to insert values
            //mysql package already does injection protection measures
            var sql = `INSERT INTO Book (userId, title, body) VALUES (${userId}, '${title}', '${body}')`;
            con.query(sql, function (err, result) {
                if (err) throw err;
                console.log("1 record inserted into Book");
            });
        });        

      });
    });
  });