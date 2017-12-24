-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 24, 2017 at 07:18 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `agri_street`
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
(1, 'pb2', 'jl malabar-ujung', '600', '100');

-- --------------------------------------------------------

--
-- Table structure for table `feedback_pebisnis`
--

CREATE TABLE `feedback_pebisnis` (
  `id_feedback` int(10) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `saran` text NOT NULL,
  `tipe_ikon` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feedback_petani`
--

CREATE TABLE `feedback_petani` (
  `id_feedback` int(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `saran` text NOT NULL,
  `tipe_ikon` varchar(100) NOT NULL
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
(1, 'Serealia', 'sekelompok tanaman yang ditanam untuk dipanen biji atau bulirnya sebagai sumber karbohidrat/pati.\r\nContoh: padi, jagung, gandum, juwawut, jali', 'http://res.cloudinary.com/dde2jdlxd/image/upload/v1511944439/serealia_h95hqh.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `kerja_sama`
--

CREATE TABLE `kerja_sama` (
  `id_kerjasama` int(10) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `id_lowongan` int(11) NOT NULL,
  `tgl_kerjasama` date NOT NULL,
  `harga_sepakat` int(20) NOT NULL,
  `status_lamaran` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lamaran_petani`
--

CREATE TABLE `lamaran_petani` (
  `id_lamar` varchar(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `id_lowongan` int(11) NOT NULL,
  `tgl_lamar` date NOT NULL,
  `harga_tawar` int(20) NOT NULL,
  `deskripsi_lamaran` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lowongan`
--

CREATE TABLE `lowongan` (
  `id_lowongan` int(11) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_kategori` int(11) NOT NULL,
  `id_alamat_pengiriman` int(11) NOT NULL,
  `foto` text NOT NULL,
  `jumlah_pelamar` int(11) NOT NULL,
  `judul_lowongan` varchar(100) NOT NULL,
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

INSERT INTO `lowongan` (`id_lowongan`, `id_pebisnis`, `id_kategori`, `id_alamat_pengiriman`, `foto`, `jumlah_pelamar`, `judul_lowongan`, `deskripsi_lowongan`, `jumlah_komoditas`, `tgl_buka`, `tgl_tutup`, `harga_awal`, `status_lowongan`) VALUES
(1, 'pb2', 1, 1, '', 0, 'Butuh Padi PT Mau Maju', 'saya butuh padi 5kg', 5, '2017-11-28', '2017-12-30', 50000, 'buka'),
(2, 'pb2', 1, 1, '', 0, 'Butuh gandung PT Sudah Jaya', 'segera gandum', 50, '2017-11-20', '2017-12-30', 500000, 'buka');

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
('pb1', 'pb1', 'pb1', 'pb1', 'pb1'),
('pb2', 'pb2', 'pb2', 'pb2', 'pb2'),
('pb3', 'aa1db5fec4b7d8bc0e4b58b99de0a4e8', 'Rya Mey', '000', '');

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
  `foto` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petani`
--

INSERT INTO `petani` (`id_petani`, `token`, `nama_petani`, `alamat`, `latitude`, `longitude`, `no_telp`, `foto`) VALUES
('pt1', '231dwfe32r2', 'Sutarman', 'Jl kiyai langgeng', '76756775', '-9879879', '6281234567890', '');

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
-- Indexes for table `feedback_pebisnis`
--
ALTER TABLE `feedback_pebisnis`
  ADD PRIMARY KEY (`id_feedback`),
  ADD KEY `id_pebisnis` (`id_pebisnis`);

--
-- Indexes for table `feedback_petani`
--
ALTER TABLE `feedback_petani`
  ADD PRIMARY KEY (`id_feedback`),
  ADD KEY `id_petani` (`id_petani`);

--
-- Indexes for table `kategori_komoditas`
--
ALTER TABLE `kategori_komoditas`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `kerja_sama`
--
ALTER TABLE `kerja_sama`
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
  MODIFY `id_alamat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `feedback_pebisnis`
--
ALTER TABLE `feedback_pebisnis`
  MODIFY `id_feedback` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `feedback_petani`
--
ALTER TABLE `feedback_petani`
  MODIFY `id_feedback` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `kategori_komoditas`
--
ALTER TABLE `kategori_komoditas`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `kerja_sama`
--
ALTER TABLE `kerja_sama`
  MODIFY `id_kerjasama` int(10) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lamaran_petani`
--
ALTER TABLE `lamaran_petani`
  MODIFY `id_lowongan` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `lowongan`
--
ALTER TABLE `lowongan`
  MODIFY `id_lowongan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `alamat`
--
ALTER TABLE `alamat`
  ADD CONSTRAINT `alamat_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`);

--
-- Constraints for table `feedback_pebisnis`
--
ALTER TABLE `feedback_pebisnis`
  ADD CONSTRAINT `feedback_pebisnis_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`);

--
-- Constraints for table `feedback_petani`
--
ALTER TABLE `feedback_petani`
  ADD CONSTRAINT `feedback_petani_ibfk_1` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`);

--
-- Constraints for table `kerja_sama`
--
ALTER TABLE `kerja_sama`
  ADD CONSTRAINT `kerja_sama_ibfk_1` FOREIGN KEY (`id_pebisnis`) REFERENCES `pebisnis` (`id_pebisnis`),
  ADD CONSTRAINT `kerja_sama_ibfk_2` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`),
  ADD CONSTRAINT `kerja_sama_ibfk_3` FOREIGN KEY (`id_lowongan`) REFERENCES `lowongan` (`id_lowongan`);

--
-- Constraints for table `lamaran_petani`
--
ALTER TABLE `lamaran_petani`
  ADD CONSTRAINT `lamaran_petani_ibfk_1` FOREIGN KEY (`id_petani`) REFERENCES `petani` (`id_petani`),
  ADD CONSTRAINT `lamaran_petani_ibfk_2` FOREIGN KEY (`id_lowongan`) REFERENCES `lowongan` (`id_lowongan`);

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
