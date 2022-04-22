-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 26, 2021 at 04:49 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `preawpung_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `account_id` int(11) NOT NULL,
  `account_username` varchar(100) NOT NULL,
  `account_password` varchar(200) NOT NULL,
  `account_name` varchar(200) NOT NULL,
  `account_gender` varchar(10) NOT NULL,
  `account_email` varchar(200) NOT NULL,
  `account_address` varchar(500) DEFAULT NULL,
  `account_tel` varchar(20) DEFAULT NULL,
  `account_birthday` date NOT NULL,
  `account_role` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`account_id`, `account_username`, `account_password`, `account_name`, `account_gender`, `account_email`, `account_address`, `account_tel`, `account_birthday`, `account_role`) VALUES
(10, 'domelaz', '46a85782f22d0d16ed50e2104b21df7f', 'Phongphanit Senaphak', 'ชาย', 'domelaz2556@gmail.com', '123/1 ม.12 ต.ศิลา อ.เมืองขอนแก่น จ.ขอนแก่น 40000', '0956956149', '2001-02-23', 'customer'),
(13, 'test', '098f6bcd4621d373cade4e832627b4f6', 'test test', 'หญิง', 'test@hjotmail.com', NULL, NULL, '2021-08-21', 'customer'),
(14, 'emp', 'ac8be4aee61f5f6e21b8c5afffb52939', 'emp emp', 'ชาย', 'emp@emp.com', NULL, NULL, '2021-08-21', 'employee'),
(15, 'lemonade', '1b908e69c96485706095ed1b37bea00a', 'Kitti Pornhub', 'หญิง', 'Kittiporn@esther.com', NULL, NULL, '2000-07-12', 'customer'),
(18, 'dd', 'b59c67bf196a4758191e42f76670ceba', 'Cus Tomer', 'ชาย', 'domelaz2310@hotmail.com', '123 Sila Khonkaen 40000', '0956956144', '2001-02-23', 'customer');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL,
  `category_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category_name`) VALUES
(1, 'สร้อยคอ'),
(2, 'กำไล'),
(3, 'แหวน');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `order_total` float NOT NULL,
  `order_status` int(11) NOT NULL,
  `order_date` date NOT NULL,
  `order_time` time NOT NULL,
  `order_tracking` varchar(100) DEFAULT NULL,
  `account_id` int(11) NOT NULL,
  `slip_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `order_total`, `order_status`, `order_date`, `order_time`, `order_tracking`, `account_id`, `slip_id`) VALUES
(37, 480, 1, '2021-09-28', '05:46:27', NULL, 10, NULL),
(38, 250, 1, '2021-09-28', '05:59:37', NULL, 10, NULL),
(39, 200, 1, '2021-09-28', '06:03:42', NULL, 10, NULL),
(40, 140, 1, '2021-09-28', '06:05:13', NULL, 10, NULL),
(41, 460, 1, '2021-09-28', '06:08:39', NULL, 10, NULL),
(42, 0, 0, '0000-00-00', '00:00:00', NULL, 10, NULL),
(43, 750, 2, '2021-10-26', '09:46:30', NULL, 18, 43),
(44, 0, 0, '0000-00-00', '00:00:00', NULL, 18, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetail`
--

CREATE TABLE `orderdetail` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_price_tmm` float NOT NULL,
  `product_amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orderdetail`
--

INSERT INTO `orderdetail` (`order_id`, `product_id`, `product_price_tmm`, `product_amount`) VALUES
(43, 8, 200, 1),
(43, 1, 250, 2);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_price` float NOT NULL,
  `product_stock_amount` int(11) NOT NULL,
  `product_pic` varchar(200) NOT NULL,
  `product_detail` varchar(400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `category_id`, `product_name`, `product_price`, `product_stock_amount`, `product_pic`, `product_detail`) VALUES
(1, 1, '3pcs Bar & Disc Charm Necklace', 250, 28, 'https://img.ltwebstatic.com/images3_pi/2020/03/24/15850245714c4c3cb40b1745b021a09646a43512a6.webp', 'เซทสร้อยคอ 3 ชิ้น ทำจากโลหะสีทอง สามารถใส่แบบแยกชิ้นได้\r\nส่วนประกอบ: 100% โลหะ\r\nวัสดุ: โลหะผสม\r\nสี: ทอง\r\nสีโลหะ: ทอง\r\nเพศ: ผู้หญิง\r\nสไตล์: ไม่เป็นทางการ'),
(2, 1, 'สร้อยคอดอกไม้', 200, 30, 'https://img.ltwebstatic.com/images3_pi/2021/04/09/1617953585bc0e3a928699a120e3f0592d35a34b39.webp', 'สร้อยคอโลหะสีทอง จี้ดอกไม้สีขาว\r\nรายละเอียด: พิมพ์ลายดอกไม้\r\nประเภท: สร้อยคอ\r\nวัสดุ: โลหะผสม\r\nสี: ทอง\r\nสีโลหะ: ทอง\r\nเพศ: ผู้หญิง\r\nสไตล์: ไม่เป็นทางการ'),
(3, 1, 'DAZY สร้อยคอลูกปัดมุกเทียมผีเสื้อ', 350, 30, 'https://img.ltwebstatic.com/images3_pi/2021/07/22/1626937015feb51cb1bf092f5ba36700b34e512863.webp', 'สร้อยคอโลหะสีทอง ร้อยด้วยลูกปัดมุกเทียม จี้ผีเสื้อ\nวัสดุ: โลหะผสม\nสี: ทอง\nเพศ: ผู้หญิง\n'),
(4, 1, 'สร้อยคอเสน่ห์หัวใจ', 250, 30, 'https://img.ltwebstatic.com/images3_pi/2021/07/23/1627008396164f3cc4bf1e743b6e00bb1c00feb604.webp', 'สร้อยคอโซ่โลหะสีเงิน จี้หัวใจสลักตัอักษร\r\nวัสดุ: โลหะผสม\r\nสี: สีเงิน\r\nเพศ: ผู้หญิง'),
(5, 1, 'สร้อยคอ Cherry Charm', 280, 30, 'https://img.ltwebstatic.com/images3_pi/2021/03/23/16164755977ef181405b969990f1d3a46d5f02cb3b.webp', 'สร้อยคอโซ่ โลหะสีเงิน จี้ผลไม้ cherry สีแดง\r\nประเภท: สร้อยคอ, สร้อยคอโซ่\r\nวัสดุ: โลหะ\r\nสี: สีเงิน\r\nสีโลหะ: สีเงิน\r\nเพศ: ผู้หญิง\r\nสไตล์: ไม่เป็นทางการ'),
(6, 2, 'สร้อยข้อมือประดับดอกไม้', 230, 30, 'https://img.ltwebstatic.com/images3_pi/2021/06/10/1623305718e8ecec2257ee796192274d6a0d81ca26.webp', 'สร้อยข้อมือโลหะสีทอง จี้ดอกไม้สีขาว\r\nวัสดุ: โลหะผสม\r\nสี: ทอง\r\nเพศ: ผู้หญิง'),
(7, 2, 'สร้อยข้อมือลูกปัดดอกไม้ 4 ชิ้น', 180, 30, 'https://img.ltwebstatic.com/images3_pi/2021/08/27/1630048735638d0c55a9836b95563feb0c7a018583.webp', 'เซทสร้อยข้อมือลูกปัด 4 ชิ้น 4 แบบ แยกชิ้นได้\r\nวัสดุ: ยางไม้\r\nสี:มัลติคัลเลอร์\r\nประเภท:ลูกปัด\r\nจำนวน:4 ชิ้น\r\n'),
(8, 2, 'สร้อยข้อมือลูกปัดดอกไม้ Charm', 200, 29, 'https://img.ltwebstatic.com/images3_pi/2021/05/08/162045329587e419b9009781f41b7cec825ad491cb.webp', 'สร้อยข้อมือลูกปัดหลากสี จี้ห้อยดอกไม้สีขาว\r\nสีโลหะ:ทอง\r\nวัสดุ:คริสตัล\r\nสี:มัลติคัลเลอร์\r\nประเภท:ลูกปัด\r\nลาย:พิมพ์ลายดอกไม้\r\nสไตล์:น่ารัก'),
(9, 2, 'สร้อยข้อมือเปิด', 140, 30, 'https://img.ltwebstatic.com/images3_pi/2021/04/27/1619494312e17348dea17d7f7b6834beafe957b815.webp', 'กำไลข้อมือโลหะสีเงินแบบเรียบ\r\nสีโลหะ:สีเงิน\r\nวัสดุ:เหล็ก\r\nสี:สีเงิน\r\nประเภท:กำไล, ข้อมือ\r\nสไตล์:ทันสมัย'),
(10, 2, 'สร้อยข้อมือลูกปัดดอกไม้', 150, 30, 'https://img.ltwebstatic.com/images3_pi/2021/05/19/1621397284d76425f9d9a5cb123ad2218b43fd0d9e.webp', 'สร้อยข้อมือลูกปัดดอกไม้หลากสี\r\nวัสดุ:พลาสติก\r\nสี:มัลติคัลเลอร์\r\nประเภท:ลูกปัด\r\nลาย:พิมพ์ลายดอกไม้\r\nสไตล์:โบโฮ'),
(11, 3, 'แหวนประดับดอกไม้', 150, 30, 'https://img.ltwebstatic.com/images3_pi/2021/06/09/162322331939740e85df3a08f55fe14ec01e2994ce.webp', 'แหวนเต็มวงแบบหนา สลักดอกไม้สีขาว\r\nสี:สีเงิน\r\nวัสดุ:ทองแดง\r\nเพศ:ผู้หญิง'),
(12, 3, 'แหวนเปิดรายละเอียดเพทาย', 180, 30, 'https://img.ltwebstatic.com/images3_pi/2021/04/16/16185442082c95aa3be70049f53d5918207b6d32f3.webp', 'แหวนดอกทิวลิปสีทองประดับเพทาย\r\nสี: ทอง\r\nลาย: ทิวลิปประดับเพทาย\r\nวัสดุ: ทองแดง\r\nเพศ: ผู้หญิง'),
(13, 3, 'DAZY แหวนดีไซน์วงรี 2 ชิ้น', 230, 30, 'https://img.ltwebstatic.com/images3_pi/2021/07/08/16257265103b4da6b9ba1db7b9078d1dc5d79774ff.webp', 'เซทแหวนโลหะสีเงิน 2 ชิ้น \r\nสี:สีเงิน\r\nวัสดุ:โลหะผสม\r\n'),
(14, 3, 'DAZY แหวนประดับผีเสื้อ', 120, 30, 'https://img.ltwebstatic.com/images3_pi/2021/07/29/1627540178944207437e85a56a6b41cc8bd45f14a5.webp', 'แหวนโลหะสีเงิน รูปผีเสื้อ\r\nสี:สีเงิน\r\nลาย: ผีเสื้อ\r\nวัสดุ:โลหะผสม\r\n'),
(15, 3, 'DAZY แหวนลูกปัดประดับดอกไม้ 2 ชิ้น', 200, 30, 'https://img.ltwebstatic.com/images3_pi/2021/07/29/1627540187f7998128ad6c49ec33bff3c424727718.webp', 'แหวนลูดปัด 2 ชิ้น จี้ดอกไม้และมุกเทียม\r\nสี:มัลติคัลเลอร์\r\nวัสดุ:ลูกปัด');

-- --------------------------------------------------------

--
-- Table structure for table `transferslip`
--

CREATE TABLE `transferslip` (
  `slip_id` int(11) NOT NULL,
  `slip_bank_name` varchar(100) NOT NULL,
  `slip_bank_number` int(11) NOT NULL,
  `slip_pic` varchar(200) NOT NULL,
  `slip_price` float NOT NULL,
  `slip_date` date NOT NULL,
  `slip_time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transferslip`
--

INSERT INTO `transferslip` (`slip_id`, `slip_bank_name`, `slip_bank_number`, `slip_pic`, `slip_price`, `slip_date`, `slip_time`) VALUES
(43, 'ไทยพาณิชย์', 1234, 'https://firebasestorage.googleapis.com/v0/b/preawpung-14128.appspot.com/o/images%2F43_2021_10_26_09_46_59?alt=media', 750, '2021-10-26', '09:46:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`account_id`),
  ADD UNIQUE KEY `account_username` (`account_username`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `transferslip`
--
ALTER TABLE `transferslip`
  ADD PRIMARY KEY (`slip_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `account_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `transferslip`
--
ALTER TABLE `transferslip`
  MODIFY `slip_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
