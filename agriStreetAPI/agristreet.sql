-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 02, 2018 at 02:20 PM
-- Server version: 5.7.20-0ubuntu0.16.04.1
-- PHP Version: 7.0.22-0ubuntu0.16.04.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agristreet`
--

-- --------------------------------------------------------

--
-- Table structure for table `alamat`
--

CREATE TABLE `alamat` (
  `id_alamat` int(11) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `deskripsi` text NOT NULL,
  `latitude` text NOT NULL,
  `longitude` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `alamat`
--

INSERT INTO `alamat` (`id_alamat`, `id_pebisnis`, `deskripsi`, `latitude`, `longitude`) VALUES
(1, 'pb2', 'jl malabar-ujung', '600', '100'),
(2, 'pb4', 'Jalan Malabar Beji', '-6.59444', '106.78917'),
(3, 'pb4', 'Jl Maraika', '-6.59444', '106.78917');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id_feedback` int(10) NOT NULL,
  `id_pengirim` varchar(10) NOT NULL,
  `id_penerima` varchar(10) NOT NULL,
  `id_kerjasama` int(10) NOT NULL,
  `saran` text NOT NULL,
  `tipe_ikon` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kategori_komoditas`
--

CREATE TABLE `kategori_komoditas` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(30) NOT NULL,
  `deskripsi_kategori` text NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kategori_komoditas`
--

INSERT INTO `kategori_komoditas` (`id_kategori`, `nama_kategori`, `deskripsi_kategori`, `foto`) VALUES
(1, 'Serealia', 'Serelia adalah sekelompok tanaman yang ditanam untuk dipanen biji atau bulirnya sebagai sumber karbohidrat/pati.\nContoh: padi, jagung, gandum, juwawut, jali', 'http://www.faunadanflora.com/wp-content/uploads/2016/07/tanaman-serealia.jpg'),
(2, 'Kacang-kacangan ', 'Produk kacang-kacangan bisa terdapat di dalam tanah, dapat pula di atas tanah berupa polong. Bentuk produknya berupa biji. Beberapa contoh yang penting adalah : kedelai, kacang hijau, kacang merah, kacang bogor.\r\n', 'https://img.okezone.com/content/2017/01/26/481/1602191/neh-segudang-manfaat-melimpah-kalau-anda-rajin-makan-kacang-kacangan-boa1Ah25f7.jpg'),
(3, 'Ubi-ubian', 'Tanaman pangan selanjutnya berasal dari jenis umbi-umbian. Tanaman umbi-umbian adalah tanaman yang ditanam untuk dipanen umbinya karena di dalam umbi terdapat kandungan karbohidrat untuk sumber nutrisi bagi tubuh. Tanaman umbi-umbian yang biasa dimanfaatkan manusia antara lain seperti ubi kayu (singkong), ubi jalar (muntul), talas, wortel, kentang, ganyong dan sebagainya.\r\n', 'http://penyakitakut.web.id/wp-content/uploads/2015/05/manfaat-ubi-jalar-untuk-kesehatan-anak.png'),
(4, 'Sayuran', 'Sayuran merupakan bahan pangan yang berasal dari tumbuhan yang sebagian besar mengandung kadar air yang tinggi dan dikonsumsi baik dalam keadaan segar maupun setelah diolah secara minimal. Jenis-jenis sayuran antara lain seperti kangkung, sawi, bayam, tomat, seledri, dan sebagainya', 'http://disehat.com/wp-content/uploads/2016/06/Sayuran-untuk-diet-yang-baik-dikonsumsi-matang-ketimbang-mentah.jpg'),
(5, 'Buah', 'Buah merupakan segala bentuk tanaman yang ditanam dan dipanen untuk diambil buahnya. Jenis-jenis tanaman buah antara lain seperti jeruk, apel, mangga, durian, kelengkeng, anggur, lemon, semangka, duku dan lain sebagainya.', 'http://3.bp.blogspot.com/-aRIS5NgyVFU/UO7GTmDs0zI/AAAAAAAAAu8/ixFEu3chKUU/s400/%D8%AB%D9%8E%D9%85%D9%8E%D8%B1%D9%8E%D8%A7%D8%AA%D9%8C.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `kerjasama`
--

CREATE TABLE `kerjasama` (
  `id_kerjasama` int(10) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `id_lowongan` int(11) NOT NULL,
  `tgl_kerjasama` date NOT NULL,
  `harga_sepakat` int(20) NOT NULL,
  `status_lamaran` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kerjasama`
--

INSERT INTO `kerjasama` (`id_kerjasama`, `id_pebisnis`, `id_petani`, `id_lowongan`, `tgl_kerjasama`, `harga_sepakat`, `status_lamaran`) VALUES
(1, 'pb4', 'pt2', 2, '2017-12-31', 500000, 'selesai');

-- --------------------------------------------------------

--
-- Table structure for table `lamaran_petani`
--

CREATE TABLE `lamaran_petani` (
  `id_lamar` int(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `id_lowongan` int(11) NOT NULL,
  `tgl_lamar` date NOT NULL,
  `harga_tawar` int(20) NOT NULL,
  `deskripsi_lamaran` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lamaran_petani`
--

INSERT INTO `lamaran_petani` (`id_lamar`, `id_petani`, `id_lowongan`, `tgl_lamar`, `harga_tawar`, `deskripsi_lamaran`) VALUES
(1, 'pt2', 1, '2017-12-31', 500, 'coba'),
(2, 'pt2', 1, '2017-12-31', 123, 'Coba coba aja sih'),
(3, 'pt2', 4, '2018-01-01', 1000, 'Saya punya pak air suci dari sungai ciliwung yang sudah teruji di IPB dan ITB.'),
(4, 'pt2', 5, '2018-01-01', 100000, 'Sama saya aja pak, jagung nya di import langsung dari asgard, manis banget kalo di gigit keluar ulatnya, penuh protein dan berkhasiat menyembuhkan berbagai macam penyakit menular seperti difteri.');

-- --------------------------------------------------------

--
-- Table structure for table `lowongan`
--

CREATE TABLE `lowongan` (
  `id_lowongan` int(11) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `id_alamat_pengiriman` int(11) NOT NULL,
  `judul_lowongan` varchar(100) NOT NULL,
  `foto` text NOT NULL,
  `deskripsi_lowongan` text NOT NULL,
  `jumlah_komoditas` int(10) NOT NULL,
  `tgl_buka` date NOT NULL,
  `tgl_tutup` date NOT NULL,
  `harga_awal` int(20) NOT NULL,
  `status_lowongan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lowongan`
--

INSERT INTO `lowongan` (`id_lowongan`, `id_pebisnis`, `id_kategori`, `id_alamat_pengiriman`, `judul_lowongan`, `foto`, `deskripsi_lowongan`, `jumlah_komoditas`, `tgl_buka`, `tgl_tutup`, `harga_awal`, `status_lowongan`) VALUES
(1, 'pb2', 1, 1, 'Butuh Padi PT Mau Maju', 'http://programhcs.com/wp-content/uploads/2016/12/Aplikasi-Suplemen-Organik-Tanaman-Untuk-Padi.jpg', 'saya butuh padi 5kg', 5, '2017-11-28', '2018-12-30', 50000, 'buka'),
(2, 'pb2', 1, 1, 'Butuh gandung PT Sudah Jaya', 'http://1.bp.blogspot.com/-7mqLnJojv7k/UlQplhlTiLI/AAAAAAAABrQ/VPiUSS6_s64/s320/Manfaat+Biji+Gandum+untuk+Kesehatan+Burung+Kicauan.jpg', 'segera gandum', 50, '2017-11-20', '2018-12-30', 500000, 'tutup'),
(3, 'pb4', 3, 2, 'Nike KW', 'http://res.cloudinary.com/dde2jdlxd/image/upload/igijhackeehcoasunzve.jpg', 'Butuh ubi nike kw', 1000, '2017-12-31', '2018-06-25', 10000000, 'buka'),
(4, 'pb4', 4, 3, 'Air Suci', 'http://res.cloudinary.com/dde2jdlxd/image/upload/zhhvxd1rmrtcvjxc5whz.jpg', 'Pengen beli air suci yang manis-manis gitu', 5, '2017-12-31', '2018-05-24', 500, 'buka'),
(5, 'pb4', 2, 2, 'Jagung Bakar Tahun Baru', 'http://res.cloudinary.com/dde2jdlxd/image/upload/bmapv3ngnlznac3riceb.jpg', 'Butuh jagung buat dijadikan bahan bakar', 1000, '2017-12-31', '2018-01-31', 500, 'buka');

-- --------------------------------------------------------

--
-- Table structure for table `pebisnis`
--

CREATE TABLE `pebisnis` (
  `id_pebisnis` varchar(10) NOT NULL,
  `token` text NOT NULL,
  `nama_pebisnis` varchar(50) DEFAULT NULL,
  `no_telp` varchar(15) NOT NULL,
  `foto` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pebisnis`
--

INSERT INTO `pebisnis` (`id_pebisnis`, `token`, `nama_pebisnis`, `no_telp`, `foto`) VALUES
('pb1', 'pb1', 'pb1', 'pb1', 'http://www.tanotofoundation.org/wp-content/uploads/2016/03/Chairman-Big.jpg'),
('pb2', 'pb2', 'pb2', 'pb2', 'http://www.jokosusilo.com/wp-content/uploads/2010/02/Pebisnis-Online-Pemula.jpg'),
('pb3', 'aa1db5fec4b7d8bc0e4b58b99de0a4e8', 'Rya Mey', '000', 'https://www.maxmanroe.com/wp-content/uploads/2015/03/Public-Speaking-Untuk-Pebisnis.jpg'),
('pb4', '5ad56921bfb171517e68406be86c04fd', 'Pebisnis Zetra Ganteng', '6281377668034', 'http://res.cloudinary.com/dde2jdlxd/image/upload/t0fntxi3ityoomxii3e5.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `petani`
--

CREATE TABLE `petani` (
  `id_petani` varchar(10) NOT NULL,
  `token` text NOT NULL,
  `nama_petani` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `latitude` text NOT NULL,
  `longitude` text NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petani`
--

INSERT INTO `petani` (`id_petani`, `token`, `nama_petani`, `alamat`, `latitude`, `longitude`, `no_telp`, `foto`) VALUES
('pt1', '231dwfe32r2', 'Sutarman', 'Jl kiyai langgeng', '76756775', '-9879879', '6281234567890', 'https://www.maxmanroe.com/wp-content/uploads/2014/12/Aplikasi-Petani-8villages.jpg'),
('pt2', 'd10e3d43dced66fa42040d238490baae', 'Petani Zetra Ganteng', 'Jl Kukusan Malaabr', '76756775', '-9879879', '6281377668034', 'http://res.cloudinary.com/dde2jdlxd/image/upload/r9gznw9jrzdzsgrjdwpl.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alamat`
--
ALTER TABLE `alamat`
  ADD PRIMARY KEY (`id_alamat`),
  ADD KEY `id_pebisnis` (`id_pebisnis`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id_feedback`),
  ADD KEY `id_kerjasama` (`id_kerjasama`);

--
-- Indexes for table `kategori_komoditas`
--
ALTER TABLE `kategori_komoditas`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `kerjasama`
--
ALTER TABLE `kerjasama`
  ADD PRIMARY KEY (`id_kerjasama`),
  ADD KEY `id_pebisnis` (`id_pebisnis`),
  ADD KEY `id_petani` (`id_petani`),
  ADD KEY `id_lowongan` (`id_lowongan`);

--
-- Indexes for table `lamaran_petani`
--
ALTER TABLE `lamaran_petani`
  ADD PRIMARY KEY (`id_lamar`),
  ADD KEY `id_petani` (`id_petani`),
  ADD KEY `id_lowongan` (`id_lowongan`);

--
-- Indexes for table `lowongan`
--
ALTER TABLE `lowongan`
  ADD PRIMARY KEY (`id_lowongan`),
  ADD KEY `id_kategori` (`id_kategori`),
  ADD KEY `id_pebisnis` (`id_pebisnis`) USING BTREE,
  ADD KEY `id_alamat_pengiriman` (`id_alamat_pengiriman`);

--
-- Indexes for table `pebisnis`
--
ALTER TABLE `pebisnis`
  ADD PRIMARY KEY (`id_pebisnis`),
  ADD UNIQUE KEY `no_telp` (`no_telp`);

--
-- Indexes for table `petani`
--
ALTER TABLE `petani`
  ADD PRIMARY KEY (`id_petani`),
  ADD UNIQUE KEY `no_telp` (`no_telp`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alamat`
--
ALTER TABLE `alamat`
  MODIFY `id_alamat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id_feedback` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `kategori_komoditas`
--
ALTER TABLE `kategori_komoditas`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `kerjasama`
--
ALTER TABLE `kerjasama`
  MODIFY `id_kerjasama` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `lamaran_petani`
--
ALTER TABLE `lamaran_petani`
  MODIFY `id_lamar` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `lowongan`
--
ALTER TABLE `lowongan`
  MODIFY `id_lowongan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `alamat`
--
ALTER TABLE `alamat`
  ADD CONSTRAINT `alamat_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`);

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`id_kerjasama`) REFERENCES `kerjasama` (`id_kerjasama`);

--
-- Constraints for table `kerjasama`
--
ALTER TABLE `kerjasama`
  ADD CONSTRAINT `kerjasama_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`),
  ADD CONSTRAINT `kerjasama_ibfk_2` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`),
  ADD CONSTRAINT `kerjasama_ibfk_3` FOREIGN KEY (`id_lowongan`) REFERENCES `lowongan` (`id_lowongan`);

--
-- Constraints for table `lamaran_petani`
--
ALTER TABLE `lamaran_petani`
  ADD CONSTRAINT `lamaran_petani_ibfk_1` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`);

--
-- Constraints for table `lowongan`
--
ALTER TABLE `lowongan`
  ADD CONSTRAINT `lowongan_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`),
  ADD CONSTRAINT `lowongan_ibfk_2` FOREIGN KEY (`id_kategori`) REFERENCES `kategori_komoditas` (`id_kategori`),
  ADD CONSTRAINT `lowongan_ibfk_3` FOREIGN KEY (`id_alamat_pengiriman`) REFERENCES `alamat` (`id_alamat`),
  ADD CONSTRAINT `lowongan_ibfk_4` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
