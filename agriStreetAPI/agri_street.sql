-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 18, 2017 at 11:34 AM
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
  `alamat` text NOT NULL,
  `latitude` text NOT NULL,
  `longitude` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id_pengguna` varchar(10) NOT NULL,
  `saran` text NOT NULL,
  `tipe_ikon` varchar(100) NOT NULL,
  `jumlah` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kategori_komoditas`
--

CREATE TABLE `kategori_komoditas` (
  `id_kategori` varchar(10) NOT NULL,
  `nama_kategori` varchar(30) NOT NULL,
  `deskripsi_kategori` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `kerja_sama`
--

CREATE TABLE `kerja_sama` (
  `id_kerjasama` varchar(10) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_petani` varchar(10) NOT NULL,
  `id_lowongan` varchar(10) NOT NULL,
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
  `id_lowongan` varchar(10) NOT NULL,
  `tgl_lamar` date NOT NULL,
  `harga_tawar` int(20) NOT NULL,
  `deskripsi_lamaran` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `lowongan`
--

CREATE TABLE `lowongan` (
  `id_lowongan` varchar(10) NOT NULL,
  `id_pebisnis` varchar(10) NOT NULL,
  `id_kategori` varchar(10) NOT NULL,
  `id_alamat_pengiriman` int(11) NOT NULL,
  `judul_lowongan` varchar(100) NOT NULL,
  `deskripsi_lowongan` text NOT NULL,
  `jumlah_komoditas` int(10) NOT NULL,
  `tgl_buka` date NOT NULL,
  `tgl_tutup` date NOT NULL,
  `harga_awal` int(20) NOT NULL,
  `status lowongan` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `pebisnis`
--

CREATE TABLE `pebisnis` (
  `id_pebisnis` varchar(10) NOT NULL,
  `token` text NOT NULL,
  `nama_pebisnis` varchar(50) DEFAULT NULL,
  `no_telp` varchar(15) NOT NULL,
  `foto` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pebisnis`
--

INSERT INTO `pebisnis` (`id_pebisnis`, `token`, `nama_pebisnis`, `no_telp`, `foto`) VALUES
('pb1', 'pb1', 'pb1', 'pb1', 'pb1'),
('pb2', 'pb2', 'pb2', 'pb2', 'pb2'),
('pb3', 'aa1db5fec4b7d8bc0e4b58b99de0a4e8', 'Rya', '6285879894380', '');

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
  ADD KEY `id_pengguna` (`id_pengguna`);

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
  MODIFY `id_alamat` int(11) NOT NULL AUTO_INCREMENT;
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
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`id_pengguna`) REFERENCES `pebisnis` (`id_pebisnis`),
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`id_pengguna`) REFERENCES `petani` (`id_petani`);

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
