-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 18, 2022 at 07:30 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sa000865048`
--

-- --------------------------------------------------------

--
-- Table structure for table `catalogue`
--

CREATE TABLE `catalogue` (
  `product_id` varchar(40) NOT NULL,
  `product_name` varchar(100) DEFAULT NULL,
  `product_description` longtext DEFAULT NULL,
  `product_price` float DEFAULT NULL,
  `product_category` varchar(25) DEFAULT NULL,
  `product_quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `catalogue`
--

INSERT INTO `catalogue` (`product_id`, `product_name`, `product_description`, `product_price`, `product_category`, `product_quantity`) VALUES
('ENT-Centre-3138', 'Brassex Inc Brassex Entertainment Centre, Black', 'Assembled dimensions: 113 x 18 x 74 (inch). Simple yet elegant design. Accommodates most flat panel TVs up to 66. ', 1219.97, 'Entertainment', 9),
('ENT-Stand-2561', 'Manor Park Simple Farmhouse Corner TV Stand for TV\'s up to 64\' - Multiple Finishes', 'Position in any corner of your living room to hold anything from your home décor to your electronics,Dimensions: 24\' H x 58\' L x 16\' W', 415.27, 'Entertainment', 10),
('ENT-Stand-3798', 'Flat file Cabinets Magazine Rack', 'TV Stand (55\'L x 13.75\'W x 16\'H)is suitable for use as a stand to place your tv on, or as an attractive piece of furniture to store all your media devices if it hangs on your wall. ', 109.99, 'Entertainment', 17),
('ENT-VTABLE-3457', 'ViscoLogic Ivory Wooden Mirrored Makeup Vanity Table (White)', 'Material: MDF and Solid Wood (Legs)- Mirror Frame: Oval. Stoarge Drawers: 5 drawers provide enough space to store cosmetics,drawers with handle for easy pullout', 210.97, 'Entertainment', 10),
('HOME-Arm-6273', 'Bowery Hill 3 Door Armoire in White', 'Amazing addition of space and storage for your room. 5 tall spacious Shelves for clothing and accesories. Includes 2 drawers.Featuring a hanging rod', 894.62, 'Home', 6),
('HOME-CBED-3457', 'Novogratz Marion Canopy Bed', 'Clearance beneath the bed can be used for storage (11) Ships in one box and it is easy to assemble. Available in black and gold in multiple sizes • Twin dimensions: 77\'L x 41\'W x 73\'H. Weight limit: 250 lb. • Shipping dimensions: 43\'L x 17.5\'W x 5.5\'H. Net weight: 43 lb. Gross weight: 49 lb.', 379.97, 'Home', 3),
('HOME-Dresser-3532', 'Little Seeds DA8036329LS Aviary Changing Dresser Topper, White', ' Sturdy wood construction and non-toxic finish. Securely fastens to the top of the Little Seeds Aviary 3-Drawer Dresser. Accommodates a standard size changing padSpecifications. Collection: Aviary. Color: White. Material: Wood. Dimension: 33.5 x 18.5 x 3 in.. Country of Origin: Vietnam- SKU: DRLAS2180', 142.67, 'Home', 8),
('HOME-SBED-3138', 'Nexera Tribeca 3 Drawer Storage Bed, Ebony', ' Twin bed has 300 lb weigh capacity. Full and queen beds have 500 pounds weight capacity.', 532.7, 'Home', 8),
('HOME-Sofa-6325', 'Canadian Made Alanis Light Grey Fabric Sofa', 'The Sofa By Fancy 4326 Alanis Fabric Sofa would look great in a family or living room setting. Each piece is made with beautiful hand-tufted back construction, leaving a decorative finish.', 869.97, 'Home', 15),
('HOME-Sofa-9329', 'Fabric Recliner in Red 8530', 'Pull the chairs Reclining lever to tip your seat back and relax. High Density foam pads on seating area so you can stay cozy. ', 348.27, 'Home', 9),
('HOME-Ward-5365', 'Atlin Designs Modern 6-Door Wood Bedroom Armoire in Black Wenge', 'Material: Particle board, laminated MDP wood Finish: Black/White. Two two-door cabinets with interior hanging rodProduct Dimensions : 71.2\'H x 63.7\'W x 18.6\'D Product Weight : 232 lbs ', 1079.27, 'Home', 6),
('OFF-Chair-6462', 'Computer Desk Chair', 'Black Chair with Loop Arms and seat height adjustment from 17.5 to 22.5(Inch)', 155.5, 'Office', 3),
('OFF-Desk-7066', 'Computer Desk ', ' Rectangle Shape Compostion wood with wood grain PU paper laminated', 89.5, 'Office', 6),
('OFF-NSTAND-6757', 'HOMETRENDS STAND, RUSTIC OAK', 'Overall table size: 15.35 x 15.67 x 20.87 •Assembled Weight: 21.12 lbs. •Package Dimensions: 18.23 in x 22.09 in x 4.92 in •Packaging Weight: 24.42 lbs.', 64.72, 'Office', 9),
('OFF-Rack-5378', 'Flat file Cabinets Magazine Rack', 'Safe and environmentlly friendly materials Size:26*18*18(cm)', 234.99, 'Office', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `catalogue`
--
ALTER TABLE `catalogue`
  ADD PRIMARY KEY (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
