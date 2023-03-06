let fs = require('fs');
let http = require('http');


let server = http.createServer();//creates event emitter

server.on('request', (req,res)=>{



    //-------------------------------------------------------
    
    let url= req.url;
    let myReadStream


    switch(url){
        case "/":
        case "/postMe.html":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'text/html' });


            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/postMe.html","utf-8");
            readAndWriteFile();
            break;
        case "/post.html":
            //use response object to set header
            console.log(JSON.stringify(req.query));
            res.writeHead(200,{'Content-Type' : 'text/html' });


            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/post.html","utf-8");
            readAndWriteFile();
            break;
        case "/forum.html":
                //use response object to set header
                res.writeHead(200,{'Content-Type' : 'text/html' });
    
    
                //read from html file, write to response obj
                myReadStream = fs.createReadStream("./public/forum.html","utf-8");
                readAndWriteFile();
            break;
        case "/css/postMe.css":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'text/css' });


            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/css/postMe.css","utf-8");
            readAndWriteFile();
            break;
        case "/js/postMe.js":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'application/javascript' });


            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/js/postMe.js","utf-8");
            readAndWriteFile();
            break;


        case "/logo.png":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/png' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/logo.png");
            readAndWriteFile();
            break;
        case "/ppicture.png":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/png' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/ppicture.png");
            readAndWriteFile();
            break;
           
        case "/messi.png":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/png' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/messi.png");
            readAndWriteFile();
            break;

        case "/wed.png":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/png' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/wed.png");
            readAndWriteFile();
            break;

        case "/drake.png":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/png' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/drake.png");
            readAndWriteFile();
            break;

        case "/taxes.jpg":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/jpg' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/taxes.jpg");
            readAndWriteFile();
            break;

        case "/lowpay.jpg":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/jpg' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/lowpay.jpg");
            readAndWriteFile();
            break;


        case "/cleancity.jpg":
            //use response object to set header
            res.writeHead(200,{'Content-Type' : 'image/jpg' });
            //read from html file, write to response obj
            myReadStream = fs.createReadStream("./public/cleancity.jpg");
            readAndWriteFile();
            break;


        
 
        default:
            console.log(url)
            break;
        

    }
    
    //view req object property url
    // console.log('client requested: ' + req.url);
    
    




    //------------------------------------------------------
    function readAndWriteFile(){
        myReadStream.on("data",(chunk)=>{   
            res.write(chunk);
        });

        myReadStream.on("end",()=>{
            res.end();
            console.log("read finished writing to res");
        });
    }


});

server.listen(8080,'127.0.0.1');
console.log('server now listening on port 8080')
