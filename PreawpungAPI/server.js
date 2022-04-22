var express = require('express')
var app = express();
var bodyParser = require('body-parser')
var mysql = require('mysql');
const { query } = require('express');
const e = require('express');
const nodemailer = require("nodemailer");
var cookie = require('cookie-parser');
var md5 = require('md5');
var session = require('express-session')

app.use(session({
    secret: 'secret',
    resave: true,
    saveUninitialized: true,
    cookie: { maxAge: 120000 }
}));

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({
    extended: true
}))
app.use(cookie())



//test API
app.get('/', function(req, res) {
    return res.send({ error: true, message: 'Hello From Preawpung' })
})


var dbConn = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'preawpung_db'
})

dbConn.connect

app.listen(3000, function() {
    console.log('Api node app is running on port 3000')
})


//Select Product
app.get('/allproduct', function(req, res) {
    dbConn.query("SELECT * FROM product ", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//login
app.get('/login/:username/:password', function(req, res) {
    username = req.params.username
    password = req.params.password

    dbConn.query("SELECT account_id,account_role FROM account WHERE account_username = ? AND account_password = ?", [username, password], function(error, results, fields) {
        if (error) {
            throw error
        }
        return res.send(results)
    })
})


//get Order id 0
app.get('/getCart/:userID', function(req, res) {

    var user_id = req.params.userID

    dbConn.query("SELECT order_id FROM `order` WHERE account_ID = ? AND order_status = 0", user_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})


//search
app.get('/searchproduct/:searchText', function(req, res) {

    var search_text = req.params.searchText
    var s = "%" + search_text + "%"

    dbConn.query("SELECT * FROM `product` WHERE product_name LIKE ? ", s, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})


app.get('/cart/:orderID', function(req, res) {

    var order_id = req.params.orderID

    dbConn.query("SELECT product.product_id, product.product_name,product.product_pic,product.product_id ,product.product_detail,orderdetail.product_price_tmm,orderdetail.product_amount, orderdetail.product_price_tmm * orderdetail.product_amount AS sum FROM orderdetail,product WHERE orderdetail.order_id = ? AND product.product_id = orderdetail.product_id", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

app.get('/getTotal/:orderID', function(req, res) {

    var order_id = req.params.orderID

    dbConn.query("SELECT SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS total FROM orderdetail,product WHERE orderdetail.order_id = ? AND product.product_id = orderdetail.product_id", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

app.get('/gethistory/:account_id', function(req, res) {
    var account_id = req.params.account_id

    dbConn.query("SELECT order_id, TIME_FORMAT(order_time,'%H:%i') AS order_time,order_total, order_status, DATE_FORMAT(order_date,'%d/%m/%y') AS order_date FROM `order` WHERE account_id = ? AND order_status != 0 AND order_status != 5 ORDER BY order_id DESC", account_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

app.get('/getorderdetail/:order_id/', function(req, res) {
    var order_id = req.params.order_id

    dbConn.query("SELECT account.account_name, order.order_id,account.account_address, account.account_tel,order.order_id,SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS order_total,SUM(orderdetail.product_amount) AS orderdetail_amount, TIME_FORMAT(order.order_time,'%H:%i') AS order_time, DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date, order.order_tracking FROM ((`order` INNER JOIN account ON order.account_id = account.account_id) INNER JOIN orderdetail ON order.order_id = orderdetail.order_id) WHERE order.order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})


app.get('/getproductdetail/:order_id/', function(req, res) {
    var order_id = req.params.order_id

    dbConn.query("SELECT product.product_name, orderdetail.product_amount,orderdetail.product_price_tmm, product.product_pic FROM orderdetail INNER JOIN product ON orderdetail.product_id = product.product_id WHERE orderdetail.order_id = ? ", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//register
app.post('/reg', function(req, res) {

    var acc = req.body

    if (!acc) {
        return res.status(400).send({ error: true, message: 'Please provide account ' });
    }
    dbConn.query("INSERT INTO account SET ?", acc, function(error, results, fields) {
        if (error) {
            return res.send({ error: true })
        }
        return res.send(results);
    })
})

//addcartorderdetail
app.post('/addcart/', function(req, res) {
    var orderdetail = req.body
    dbConn.query("SELECT * FROM orderdetail WHERE order_id = ? AND product_id = ?", [orderdetail.order_id, orderdetail.product_id], function(er, resu, fild) {
        if (er) throw er
        if (resu.length > 0) {
            dbConn.query("UPDATE orderdetail SET product_amount = product_amount + 1 WHERE order_id = ? AND product_id = ?", [orderdetail.order_id, orderdetail.product_id], function(err, ress, field) {
                if (err) throw err
                dbConn.query("SELECT SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS order_total FROM orderdetail where order_id = ?", orderdetail.order_id, function(error, resluts, fields) {
                    if (error) throw error
                    dbConn.query("UPDATE `order` SET order_total = ? WHERE order_id = ?", [resluts[0].order_total, orderdetail.order_id], function(error, reults, fields) {
                        if (error) throw error
                    })
                })
                return res.send(ress)
            })
        } else {
            dbConn.query("INSERT INTO orderdetail SET ?", orderdetail, function(error, results, fields) {
                if (error) throw error
                dbConn.query("SELECT SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS order_total FROM orderdetail where order_id = ?", orderdetail.order_id, function(error, resluts, fields) {
                    if (error) throw error
                    dbConn.query("UPDATE `order` SET order_total = ? WHERE order_id = ?", [resluts[0].order_total, orderdetail.order_id], function(error, reults, fields) {
                        if (error) throw error
                    })
                })
                return res.send(results);
            })
        }
    })
})

//createOrder
app.post('/createorder/', function(req, res) {
    var order = req.body
    dbConn.query("INSERT INTO `order` SET ?", order, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//getlastorder
app.get('/getlastorder/:account_id', function(req, res) {
    var account_id = req.params.account_id
    dbConn.query("SELECT order_id FROM `order` WHERE account_id = ? ORDER BY order_id DESC LIMIT 1 ", account_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//Profile
app.get('/getprofile/:account_id/', function(req, res) {
    var account_id = req.params.account_id

    dbConn.query("SELECT account_id,account_name,account_email,account_gender,account_tel,account_address,DATE_FORMAT(account_birthday,'%Y-%m-%d') AS account_birthday FROM account WHERE account_id = ?", account_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//Profileemp
app.get('/getprofileemp/:account_id/', function(req, res) {
    var account_id = req.params.account_id

    dbConn.query("SELECT account_id,account_name,account_email,account_gender,DATE_FORMAT(account_birthday,'%Y-%m-%d') AS account_birthday FROM account WHERE account_id = ?", account_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//add Amount
app.put('/add/:order_id/:product_id', function(req, res) {
    var order_id = req.params.order_id
    var product_id = req.params.product_id

    dbConn.query("UPDATE orderdetail SET product_amount = product_amount + 1 WHERE order_id = ? AND product_id = ?", [order_id, product_id], function(error, results, fields) {
        if (error) throw error
        dbConn.query("SELECT SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS order_total FROM orderdetail where order_id = ?", order_id, function(error, resluts, fields) {
            if (error) throw error
            dbConn.query("UPDATE `order` SET order_total = ? WHERE order_id = ?", [resluts[0].order_total, order_id], function(error, reults, fields) {
                if (error) throw error
            })
        })
        return res.send(results)
    })
})

//remove amount
app.put('/remove/:order_id/:product_id', function(req, res) {
    var order_id = req.params.order_id
    var product_id = req.params.product_id

    dbConn.query("UPDATE orderdetail SET product_amount = product_amount - 1 WHERE order_id = ? AND product_id = ?", [order_id, product_id], function(error, results, fields) {
        if (error) throw error
        dbConn.query("SELECT SUM(orderdetail.product_amount * orderdetail.product_price_tmm) AS order_total FROM orderdetail where order_id = ?", order_id, function(error, resluts, fields) {
            if (error) throw error
            dbConn.query("UPDATE `order` SET order_total = ? WHERE order_id = ?", [resluts[0].order_total, order_id], function(error, reults, fields) {
                if (error) throw error
            })
        })
        return res.send(results)
    })
})

//confirmorder
app.put('/confirmorder/:order_id/:date/:time', function(req, res) {
    var order_id = req.params.order_id
    var order_date = req.params.date
    var order_time = req.params.time


    dbConn.query("UPDATE `order` SET order_status = 1, order_date = ?, order_time = ? WHERE `order_id` = ?", [order_date, order_time, order_id], function(error, resules, fields) {
        if (error) throw error
        if (resules) {
            dbConn.query("SELECT SUM(product_price_tmm * product_amount) AS total FROM `orderdetail` WHERE order_id = ?", order_id, function(err, ress, field) {
                if (err) throw err
                if (ress) {
                    dbConn.query("UPDATE `order` SET order_total = ? WHERE order_id = ?", [ress[0].total + 50, order_id], function(er, re, fi) {
                        if (er) throw er
                        dbConn.query("SELECT product_id,product_amount FROM orderdetail WHERE order_id = ? ", order_id, function(error1, results1, fields1) {
                            if (error1) throw error1
                            let length = Object.keys(results1).length
                            for (let i = 0; i < length; i++) {
                                dbConn.query("UPDATE `product` SET product_stock_amount = product_stock_amount - ? WHERE product_id = ?", [results1[i].product_amount, results1[i].product_id], function(error2, results2, fields2) {
                                    if (error2) throw error2
                                })
                            }
                            return res.send(re)
                        })
                    })
                }
            })
        }
    })
})

//cancel order
app.put('/cancelorder/:order_id', function(req, res) {
    var order_id = req.params.order_id
    dbConn.query("UPDATE `order` SET order_status = 5 WHERE order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        dbConn.query("SELECT product_id,product_amount FROM orderdetail WHERE order_id = ? ", order_id, function(error_select, results_select, fields_delect) {
            if (error_select) throw error_select
            let length = Object.keys(results_select).length
            for (let i = 0; i < length; i++) {
                dbConn.query("UPDATE `product` SET product_stock_amount = product_stock_amount + ? WHERE product_id = ?", [results_select[i].product_amount, results_select[i].product_id], function(error2, results2, fields2) {
                    if (error2) throw error2
                })
            }
            return res.send(results)
        })
    })
})


//addslip
app.put('/addslip/', function(req, res) {
    var slipdetail = req.body

    dbConn.query("UPDATE `order` SET order_status = 2 WHERE order_id = ?", slipdetail.slip_id, function(error, results, fields) {
        if (error) throw error
        dbConn.query("INSERT INTO `transferslip` SET ? ", slipdetail, function(error1, results1, fields1) {
            if (error1) throw error1
            dbConn.query("UPDATE `order` SET slip_id = ? WHERE order_id = ?", [slipdetail.slip_id, slipdetail.slip_id], function(error3, results3, fields3) {
                if (error3) throw error3
                return res.send(results3)
            })
        })
    })
})


//0 ยังไม่ยืนยัน 1 ยังไม่ชำระ 2 รอตรวจสอบ 3 รอจัดส่ง 4 จัดส่งแล้ว 5 ยกเลิก 

//checkslip
app.put('/checkslip/:order_id', function(req, res) {
    var order_id = req.params.order_id
    dbConn.query("UPDATE `order` SET order_status = 3 WHERE order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//updatetecking
app.put('/updatetracking/:order_id', function(req, res) {
    var order_id = req.params.order_id
    var raw_track = req.body
    var order_tracking = raw_track.order_tracking
    dbConn.query("UPDATE `order` SET order_status = 4, order_tracking = ? WHERE order_id = ?", [order_tracking, order_id], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//select all paid
app.get('/allpaid', function(req, res) {
    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 2 ORDER BY order.order_id DESC", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//select all track
app.get('/alltrack', function(req, res) {
    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 3 ORDER BY order.order_id DESC", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//select all order
app.get('/allorder', function(req, res) {
    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic,order.order_status FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 4 OR order_status = 6 ORDER BY order.order_id DESC", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//checkdetail
app.get('/checkdetail/:order_id', function(req, res) {
        var order_id = req.params.order_id
        dbConn.query("SELECT account.account_name,account.account_tel,account.account_address,transferslip.slip_price,transferslip.slip_bank_name,transferslip.slip_bank_number,transferslip.slip_pic,DATE_FORMAT(transferslip.slip_date, '%d/%m/%y') AS slip_date,TIME_FORMAT(transferslip.slip_time,'%H:%i') AS slip_time FROM ((`order`INNER JOIN account ON order.account_id = account.account_id) INNER JOIN transferslip ON order.slip_id = transferslip.slip_id) WHERE order_id = ?", order_id, function(error, results, fields) {
            if (error) throw error
            return res.send(results[0])
        })
    })
    //orderdetail
app.get('/hisdetail/:order_id', function(req, res) {
    var order_id = req.params.order_id
    dbConn.query("SELECT account.account_name,account.account_tel,account.account_address,transferslip.slip_price,transferslip.slip_bank_name,transferslip.slip_bank_number,order.order_tracking,transferslip.slip_pic,DATE_FORMAT(transferslip.slip_date, '%d/%m/%y') AS slip_date,TIME_FORMAT(transferslip.slip_time,'%H:%i') AS slip_time FROM ((`order`INNER JOIN account ON order.account_id = account.account_id) INNER JOIN transferslip ON order.slip_id = transferslip.slip_id) WHERE order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results[0])
    })
})

//delete order detail
app.delete('/delorderdetail/:order_id/:product_id', function(req, res) {
    let order_id = req.params.order_id
    let product_id = req.params.product_id

    dbConn.query("DELETE FROM orderdetail WHERE order_id = ? AND product_id = ?", [order_id, product_id], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//search check
app.get('/searchcheck/:order_id', function(req, res) {
    order_id = req.params.order_id

    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 2 AND order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//search track
app.get('/searchtrack/:order_id', function(req, res) {
    order_id = req.params.order_id

    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 3 AND order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//search Order
app.get('/searchorder/:order_id', function(req, res) {
    order_id = req.params.order_id

    dbConn.query("SELECT order.order_id,DATE_FORMAT(order.order_date, '%d/%m/%y') AS order_date,transferslip.slip_pic FROM `order` INNER JOIN transferslip ON order.slip_id = transferslip.slip_id WHERE order_status = 4 AND order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//get สร้อย Category
app.get('/getnecklace', function(req, res) {
    dbConn.query("SELECT * FROM product WHERE category_id = 1 ", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//get กำไล Category
app.get('/getbangle', function(req, res) {
    dbConn.query("SELECT * FROM product WHERE category_id = 2 ", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//get แหวน Category
app.get('/getring', function(req, res) {
    dbConn.query("SELECT * FROM product WHERE category_id = 3 ", function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//delete product from cart
app.delete('/delproduct/:order_id/:product_id', function(req, res) {
    let order_id = req.params.order_id
    let product_id = req.params.product_id
    dbConn.query("DELETE FROM `orderdetail` WHERE order_id = ? AND product_id = ?", [order_id, product_id], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//update account customer
app.put('/updateprofilecus/:account_id', function(req, res) {
    let account_id = req.params.account_id
    let account = req.body
    dbConn.query("UPDATE account SET ? WHERE account_id = ?", [account, account_id], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//update account employee
app.put('/updateprofileemp/:account_id', function(req, res) {
    let account_id = req.params.account_id
    let account = req.body
    dbConn.query("UPDATE account SET ? WHERE account_id = ?", [account, account_id], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

// //update password
app.put('/newpassword/:account_email', function(req, res) {
    let email = req.params.account_email
    let password = req.body.account_password
    dbConn.query("UPDATE account SET account_password = ? WHERE account_email = ?", [password, email], function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})
app.put('/ordersuccess/:order_id', function(req, res) {
    let order_id = req.params.order_id

    dbConn.query("UPDATE `order` SET order_status = 6 WHERE order_id = ?", order_id, function(error, results, fields) {
        if (error) throw error
        return res.send(results)
    })
})

//send email
app.get('/sendemail/:email', function(req, res) {

    let email = req.params.email
    let otp = Math.floor(Math.random() * 90000) + 10000;
    let ref = Math.floor(Math.random() * 9000) + 1000;

    var transporter = nodemailer.createTransport({
        service: 'gmail',
        auth: {
            user: 'domelaz2556@gmail.com',
            pass: 'Dome383-4'
        }
    });


    dbConn.query("SELECT account_name,account_username FROM account WHERE account_email = ?", email, function(error, results, fields) {
        if (error) throw error
        if (results < 1) {
            return res.send({ error: true })
        } else {
            let en_otp = md5(otp.toString())
            req.session.otp = en_otp

            var mailOptions = {
                from: 'domelaz2556@gmail.com',
                to: email,
                subject: 'PreawPung : ยืนยันตัวตน',
                // text: 'สวัสดีคุณ ' + results[0].account_name +'\n ชื่อผู้ใช้ : '+results[0].account_username+'\n รหัสยืนยันตัวตน คือ ' + otp
                html: "<div style='background-color: #f9f7f7; height: 350px; width: 350px; border-radius: 50px;'><h2 style='text-align: center;padding: 20px;'>สวัสดีคุณ<br> " + results[0].account_name + "</h2><h3 style='text-align: center;padding: 15px;'>ชื่อผู้ใช้ " + results[0].account_username + "</h3><h3 style='text-align: center;padding: 10px;'>รหัสยืนยันตัวตน</h3><h1 style='text-align: center;padding: 15px;color:#ffcc79;'>" + otp + "</h1></div > "
            };

            transporter.sendMail(mailOptions, function(error, info) {
                if (error) {
                    console.log(error);
                } else {
                    console.log('Email sent: ' + info.response);
                }
            });

            return res.send({ error: false, otp: req.session.otp })
        }
    })
})

module.exports = app;