CREATE DATABASE  IF NOT EXISTS `cudia_dk_db` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cudia_dk_db`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: mysql24.unoeuro.com    Database: cudia_dk_db
-- ------------------------------------------------------
-- Server version	8.0.32-24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hasSeen`
--

DROP TABLE IF EXISTS `hasSeen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hasSeen` (
  `username` varchar(255) NOT NULL,
  `medias` varchar(1023) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasSeen`
--

LOCK TABLES `hasSeen` WRITE;
/*!40000 ALTER TABLE `hasSeen` DISABLE KEYS */;
INSERT INTO `hasSeen` VALUES ('Jack','Casablanca, Star Wars, 2001 A Space Odyssey, Dr. Strangelove Or How I Learned To Stop Worrying And Love The Bomb, A Streetcar Named Desire'),('Julius',''),('bjark','Sunset Boulevard'),('Faggotjack',''),('jack',''),('signe','Star Wars'),('s',''),('h',''),('j','');
/*!40000 ALTER TABLE `hasSeen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `genre` varchar(512) NOT NULL,
  `rating` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES (1,'The Godfather',1972,' Crime, Drama',9.2),(2,'The Shawshank Redemption',1994,' Drama',9.3),(3,'Schindler\'s List',1993,' Biography, Drama, History',8.9),(4,'Raging Bull',1980,' Biography, Drama, Sport',8.2),(5,'Casablanca',1942,' Drama, Romance, War',8.5),(6,'Citizen Kane',1941,' Drama, Mystery',8.4),(7,'Gone With The Wind',1939,' Drama, History, Romance',8.2),(8,'The Wizard Of Oz',1939,' Adventure, Family, Fantasy',8),(9,'One Flew Over The Cuckoo\'s Nest',1975,' Drama',8.7),(10,'Lawrence Of Arabia',1962,' Adventure, Biography, Drama',8.3),(11,'Vertigo',1958,' Mystery, Romance, Thriller',8.3),(12,'Psycho',1960,' Horror, Mystery, Thriller',8.5),(13,'The Godfather part II',1974,' Crime, Drama',9),(14,'On The Waterfront',1954,' Crime, Drama, Thriller',8.2),(15,'Sunset Boulevard',1950,' Drama, Film-Noir',8.4),(16,'Forrest Gump',1994,' Drama, Romance',8.8),(17,'The Sound Of Music',1965,' Biography, Drama, Family, Musical',8),(18,'12 Angry Men',1957,' Crime, Drama',8.9),(19,'West Side Story',1961,' Crime, Drama',7.6),(20,'Star Wars',1977,' Action, Adventure, Family',8.6),(21,'2001 A Space Odyssey',1968,' Adventure, Sci-fi',8.3),(22,'ET',1982,' Family, Sci-fi',7.9),(23,'The Silence Of The Lambs',1991,'Crime, Drama, Thriller',8.6),(24,'Chinatown',1974,' Drama, Mystery, Thriller',8.2),(25,'The Bridge Over The River Kwai',1957,' Adventure, Drama, War',8.2),(26,'Singin\' In The Rain',1952,' Comedy, Musical, Romance',8.3),(27,'It\'s A Wonderful Life',1946,' Drama, Family, Fantasy',8.6),(28,'Dr. Strangelove Or How I Learned To Stop Worrying And Love The Bomb',1964,' Comedy, War',8.4),(29,'Some Like It Hot',1959,' Comedy, Romance',8.2),(30,'Ben Hur',1959,' Adventure, Drama, History',8.1),(31,'Apocalypse Now',1979,' Drama, War',8.5),(32,'Amadeus',1984,' Biography, Drama, History',8.3),(33,'Lord Of The Rings - The Return Of The King',2003,' Action, Adventure, Drama',8.9),(34,'Gladiator',2000,' Action, Adventure, Drama',8.5),(35,'Titanic',1997,' Drama, Romance',7.8),(36,'From Here To Eternity',1953,' Drama, Romance, War',7.7),(37,'Saving Private Ryan',1998,' Drama, War',8.6),(38,'Unforgiven',1992,' Drama, Western',8.2),(39,'Raiders Of The Lost Ark',1981,' Action, Adventure',8.5),(40,'Rocky',1976,' Drama, Sport',8.1),(41,'A Streetcar Named Desire',1951,' Drama',8),(42,'A Philadelphia Story',1940,' Comedy, Romance',8),(43,'To Kill A Mockingbird',1962,' Crime, Drama',8.3),(44,'An American In Paris',1951,' Drama, Musical, Romance',7.2),(45,'The Best Years Of Our Lives',1946,' Drama, Romance, War',8.1),(46,'My Fair Lady',1964,' Drama, Family, Musical',7.9),(47,'A Clockwork Orange',1971,' Crime, Drama, Sci-fi',8.3),(48,'Doctor Zhivago',1965,' Drama, Romance, War',8),(49,'The Searchers',1956,' Adventure, Drama, Western',8),(50,'Jaws',1975,' Adventure, Drama, Thriller',8),(51,'Patton',1970,' Biography, Drama, War',8),(52,'Butch Cassidy And The Sundance Kid',1969,' Biography, Crime, Drama',8.1),(53,'The Treasure Of The Sierra Madre',1948,' Adventure, Drama, Western',8.3),(54,'The Good, The Bad And The Ugly',1966,' Western',8.9),(55,'The Apartment',1960,' Comedy, Drama, Romance',8.3),(56,'Platoon',1986,' Drama, War',8.1),(57,'High Noon',1952,' Action, Drama, Thriller',8),(58,'Braveheart',1995,' Biography, Drama, History',8.4),(59,'Dances With Wolves',1990,' Adventure, Drama, Western',8),(60,'Jurassic Park',1993,' Adventure, Sci-fi, Thriller',8.1),(61,'The Exorcist',1973,' Horror',8),(62,'The Pianist',2002,' Biography, Drama, Music',8.5),(63,'Goodfellas',1990,' Crime, Drama',8.7),(64,'The Deer Hunter',1978,' Drama, War',8.1),(65,'All Quiet On The Western Front',1930,' Drama, War',8.1),(66,'Bonny And Clyde',1967,' Action, Biography, Crime',7.9),(67,'The French Connection',1971,' Action, Crime, Drama',7.8),(68,'City Lights',1931,' Comedy, Drama, Romance',8.5),(69,'It Happened One Night',1934,' Comedy, Romance',8.1),(70,'A Place In The Sun',1951,' Drama, Romance',7.8),(71,'Midnight Cowboy',1969,' Drama',7.9),(72,'Mr Smith Goes To Washington',1939,' Comedy, Drama',8.2),(73,'Rain Man',1988,' Drama',8),(74,'Annie Hall',1977,' Comedy, Romance',8),(75,'Fargo',1996,' Crime, Drama, Thriller',8.1),(76,'Giant',1956,' Drama, Western',7.7),(77,'Shane',1953,' Drama, Western',7.7),(78,'Grapes Of Wrath',1940,' Drama, History',8.1),(79,'The Green Mile',1999,' Crime, Drama, Fantasy',8.5),(80,'Close Encounters',1977,' Drama, Sci-fi',7.7),(81,'Nashville',1975,' Comedy, Drama, Music',7.8),(82,'Network',1976,' Drama',8.1),(83,'The Graduate',1967,' Comedy, Drama, Romance',8),(84,'American Graffiti',1973,' Comedy, Drama',7.5),(85,'Pulp Fiction',1994,' Crime, Drama',8.9),(86,'Terms of Endearment',1983,' Comedy, Drama',7.4),(87,'Good Will Hunting',1997,' Drama, Romance',8.3),(88,'The African Queen',1951,' Adventure, Drama, Romance',7.9),(89,'Stagecoach',1939,' Adventure, Western',7.9),(90,'Mutiny On The Bounty',1935,' Adventure, Biography, Drama',7.8),(91,'The Great Dictator',1940,' Comedy, Drama, War',8.5),(92,'Double Indemnity',1944,' Crime, Drama, Film-Noir',8.3),(93,'The Maltese Falcon',1941,' Film-Noir, Mystery',8.1),(94,'Wuthering Heights',1939,' Drama, Romance',7.7),(95,'Taxi Driver',1976,' Crime, Drama',8.3),(96,'Rear Window',1954,' Mystery, Thriller',8.5),(97,'The Third Man',1949,' Film-Noir, Mystery, Thriller',8.2),(98,'Rebel Without A Cause',1955,' Drama',7.8),(99,'North By Northwest',1959,' Adventure, Mystery, Thriller',8.3),(100,'Yankee Doodle Dandy',1942,' Biography, Drama, Musical',7.7);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `savedMedias`
--

DROP TABLE IF EXISTS `savedMedias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savedMedias` (
  `username` varchar(255) NOT NULL,
  `medias` varchar(1023) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `savedMedias`
--

LOCK TABLES `savedMedias` WRITE;
/*!40000 ALTER TABLE `savedMedias` DISABLE KEYS */;
INSERT INTO `savedMedias` VALUES ('Jack','Casablanca, Dr. Strangelove Or How I Learned To Stop Worrying And Love The Bomb'),('Julius',''),('bjark','Star Wars, The Godfather part II'),('Faggotjack',''),('jack',''),('signe',''),('s',''),('h',''),('j','');
/*!40000 ALTER TABLE `savedMedias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `series`
--

DROP TABLE IF EXISTS `series`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `series` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `year` varchar(16) NOT NULL,
  `genre` varchar(511) NOT NULL,
  `rating` double NOT NULL,
  `seasons` varchar(511) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `series`
--

LOCK TABLES `series` WRITE;
/*!40000 ALTER TABLE `series` DISABLE KEYS */;
INSERT INTO `series` VALUES (1,'Twin Peaks',' 1990-1991',' Crime, Drama, Mystery',8.8,' 1-8, 2-22'),(2,'The Sopranos',' 1999-2007',' Crime, Drama',9.2,' 1-13, 2-13, 3-13, 4-13, 5-13, 6-21'),(3,'Game Of Thrones',' 2011-',' Action, Adventure, Drama',9.5,' 1-10, 2-10, 3-10, 4-10, 5-10, 6-10, 7-7'),(4,'Breaking Bad',' 2008-2013',' Crime, Drama, Thriller',9.5,' 1-7, 2-13, 3-13, 4-13, 5-16'),(5,'The Larry Sanders Show',' 1992-1998',' Comedy',8.3,' 1-13, 2-18, 3-17, 4-17, 5-13, 6-12'),(6,'Curb Your Enthusiasm',' 2000-',' Comedy',8.7,' 1-10, 2-10, 3-10, 4-10, 5-10, 6-10, 7-10, 8-10, 9-10'),(7,'Lost',' 2004-2010',' Adventure, Drama, Fantasy',8.4,' 1-25, 2-24, 3-23, 4-14, 5-17, 6-18'),(8,'Louie',' 2010-2015',' Comedy, Drama',8.6,' 1-13, 2-13, 3-13, 4-14, 5-8'),(9,'Cheers',' 1982-1993',' Comedy, Drama',7.8,' 1-22, 2-22, 3-25, 4-26, 5-26, 6-25, 7-22, 8-26, 9-27, 10-26, 11-28'),(10,'Scener ur ett Ã¯Â¿Â½ktenskap',' 1973',' Drama',8.5,' 1-6'),(11,'Buffy The Vampire Slayer',' 1996-2003',' Action, Drama, Fantasy',8.2,' 1-12, 2-22, 3-22, 4-22, 5-22, 6-22, 7-22'),(12,'The Shield',' 2002-2008',' Crime, Drama, Thriller',8.7,' 1-13, 2-13, 3-15, 4-13, 5-11, 6-10, 7-13'),(13,'Deadwood',' 2004-2006',' Crime, Drama, History',8.7,' 1-12, 2-12, 3-12'),(14,'The Marvelous Mrs Maisel',' 2017- ',' Comedy, Drama',8.7,' 1-8'),(15,'Horace and Pete\'s',' 2016',' Comedy, Drama',8.6,' 1-10'),(16,'Top Of The Lake',' 2013- ',' Crime, Drama, Mystery',7.5,' 1-7, 2-6'),(17,'The Simpsons',' 1989- ',' Animation, Comedy',8.7,' 1-13, 2-22, 3-24, 4-22, 5-22, 6-25, 7-25, 8-25, 9-25, 10-23, 11-22, 12-21, 13-22, 14-22, 15-22, 16-21, 17-22, 18-22, 19-20, 20-21, 21-23, 22-22, 23-22, 24-22, 25-22, 26-22, 27-22, 28-22, 29-21'),(18,'The Walking Dead',' 2010- ',' Drama, Horror, Sci-fi',8.4,' 1-6, 2-13, 3-16, 4-16, 5-16, 6-16, 7-16, 8-16, 9-16'),(19,'The Wire',' 2001-2008',' Crime, Drama, Thriller',9.3,' 1-13, 2-12, 3-12, 4-13, 5-10'),(20,'Welt Am Draht',' 1973 ',' Crime, Mystery, Sci-fi',7.9,' 1-2'),(21,'House Of Cards',' 2013-2018',' Drama',8.9,' 1-13, 2-13, 3-13, 4-13, 5-13, 6-8'),(22,'Fargo',' 2014- ',' Crime, Drama, Thriller',9,' 1-10'),(23,'Angel',' 1999-2004',' Action, Drama, Fantasy',8,' 1-22, 2-22, 3-22, 4-22, 5-22'),(24,'V',' 1984-1985',' Action, Adventure, Sci-fi',7.3,' 1-13'),(25,'Jessica Jones',' 2015- ',' Action, Crime, Drama',8.1,' 1-13, 2-13'),(26,'Daredevil',' 2015- ',' Action, Crime, Drama',8.7,' 1-13, 2-13, 3-13'),(27,'Seinfeld',' 1989-1998',' Comedy',8.9,' 1-5, 2-12, 3-23, 4-24, 5-22, 6-24, 7-24, 8-22, 9-24'),(28,'Slings And Arrows',' 2003-2006',' Comedy',8.5,' 1-6, 2-6, 3-6'),(29,'I Claudius',' 1976','  Biography, Drama, History',8.9,' 1-12'),(30,'Rome',' 2005-2007',' Action, Drama, History',8.8,' 1-12, 2-10'),(31,'Ray Donovan',' 2013- ',' Crime, Drama',8.3,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-12'),(32,'Better Call Saul',' 2015- ',' Crime, Drama',8.7,' 1-10, 2-10, 3-10, 4-10'),(33,'The Honeymooners',' 1955-1956',' Comedy, Family',8.7,' 1-39'),(34,'Spaced',' 1999-2001',' Action, Comedy',8.6,' 1-7, 2-7'),(35,'The Twilight Zone',' 1959-1964',' Fantasy, Horror, Mystery',9,' 1-36, 2-29, 3-37, 4-18, 5-36'),(36,'Fraiser',' 1993-2004',' Comedy',8.1,' 1-24, 2-24, 3-24, 4-24, 5-24, 6-24, 7-24, 8-24, 9-24, 10-24, 11-24'),(37,'Alfred Hitchcock Presents',' 1955-1962',' Crime, Drama, Mystery',8.6,' 1-39, 2-39, 3-39, 4-36, 5-38, 6-38, 7-38'),(38,'I Love Lucy',' 1951-1957',' Comedy, Family',8.3,' 1-35, 2-31, 3-31, 4-30, 5-26, 6-27'),(39,'24',' 2001-2010',' Action, Crime, Drama',8.4,' 1-24, 2-24, 3-24, 4-24, 5-24, 6-24.7-24, 8-24'),(40,'The Americans',' 2013-2018',' Crime, Drama, Mystery',8.4,' 1-13, 2-13, 3-13, 4-13, 5-13, 6-10'),(41,'Girls',' 2012-2017',' Comedy, Drama',7.3,' 1-10, 2-10, 3-12, 4-10, 5-10'),(42,'Mad Men',' 2007-2015',' Drama',8.6,' 1-13, 2-13, 3-13, 4-13, 5-13, 6-13, 7-14'),(43,'Northern Exposure',' 1990-1995',' Comedy, Drama, Fantasy',8.3,' 1-8, 2-7, 3-23, 4-25, 5-24, 6-23'),(44,'Star Trek',' 1966-1969',' Action, Adventure, Sci-fi',8.3,' 1-29, 2-26, 3-24'),(45,'Big Little Lies',' 2017 ',' Crime, Drama, Mystery',8.6,' 1-7'),(46,'Firefly',' 2002-2003',' Adventure, Drama, Sci-fi',9,' 1-14'),(47,'Spartacus',' 2010-2013',' Action, Adventure, Biography',8.5,' 1-13, 2-10, 3-10'),(48,'Mr Show With Bob And David',' 1995-1998',' Comedy',8.3,' 1-4, 2-6, 3-10, 4-10'),(49,'Homeland',' 2011-',' Crime, Drama, Mystery',8.3,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-12, 7-12'),(50,'Show Me A Hero',' 2015',' Drama, History',8.1,' 1-6'),(51,'Crisis In Six Scenes',' 2016',' Comedy',6.6,' 1-6'),(52,'Batman',' 1966-1968',' Action, Adventure, Comedy',7.5,' 1-34, 2-60, 3-26'),(53,'Luke Cage',' 2016-2018',' Action, Crime, Drama',7.5,' 1-13, 2-13'),(54,'South Park',' 1997-',' Animation, Comedy',8.7,' 1-13, 2-18, 3-17, 4-17, 5-14, 6-17, 7-15, 8-14, 9-14, 10-14, 11-14, 12-14, 13-14, 14-14, 15-14, 16-14, 17-10, 18-10, 19-10, 20-10, 21-10, 22-10'),(55,'Band Of Brothers',' 2001',' Action, Drama, History',9.5,' 1-10'),(56,'Married With Children',' 1986-1997',' Comedy',8.1,' 1-13, 2-22, 3-22, 4-23, 5-25, 6-26, 7-26, 8-26, 9-26, 10-26, 11-24'),(57,'Family Ties',' 1982-1989',' Comedy, Family',7.2,' 1-21, 2-22, 3-22, 4-22, 5-24, 6-24, 7-20'),(58,'Lonesome Dove',' 1989',' Adventure, Drama, Western',8.7,' 1-4'),(59,'Pride And Prejudice',' 1995',' Drama, Romance',8.9,' 1-6'),(60,'Flying Blind',' 1992-1993',' Comedy',8,' 1-22'),(61,'Battlestar Galactica',' 2004-2009',' Action, Adventure, Drama',8.7,' 1-13, 2-20, 3-20, 4-20'),(62,'Lucky Louie',' 2006-2007',' Comedy',8,' 1-13'),(63,'Dekalog',' 1989-1990',' Drama',9.1,' 1-10'),(64,'It\'s Garry Shandling\'s Show',' 1986-1990',' Comedy',7.6,' 1-16, 2-17, 3-20, 4-19'),(65,'On The Air',' 1992',' Comedy',7.4,' 1-7'),(66,'All In The Family',' 1971-1979',' Comedy, Drama',7.4,' 1-13, 2-24, 3-24, 4-24, 5-23, 6-24, 7-25, 8-24, 9-24'),(67,'Entourage',' 2004-2011',' Comedy, Drama',8.5,' 1-8, 2-14, 3-20, 4-12, 5-12, 6-12, 7-10, 8-8'),(68,'Ally McBeal',' 1997-2002',' Comedy, Drama, Fantasy',6.8,' 1-23, 2-23, 3-21, 4-23, 5-22'),(69,'The Strain',' 2014-2017',' Drama, Horror, Thriller',7.4,' 1-13, 2-13, 3-10, 4-10'),(70,'The Man In The High Castle',' 2015- ',' Drama, Sci-fi, Thriller',8.1,' 1-10, 2-10, 3-10'),(71,'Sons Of Anarchy',' 2008-2014',' Crime, Drama, Thriller',8.6,' 1-13, 2-13, 3-13, 4-14, 5-13, 6-13, 7-13'),(72,'The Punisher',' 2017 ',' Action, Adventure, Crime',8.6,' 1-13'),(73,'Boardwalk Empire',' 2010-2014',' Crime, Drama, History',8.6,' 1-12, 2-12, 3-12, 4-12, 5-8'),(74,'Damages',' 2007-2012',' Crime, Drama, Mystery',8.1,' 1-13, 2-13, 3-13, 4-10, 5-10'),(75,'Banshee',' 2013-2016',' Action, Crime, Drama',8.4,' 1-10, 2-10, 3-10, 4-8'),(76,'Planet Earth',' 2006',' Documentary',9.4,' 1-11'),(77,'Freaks And Geeks',' 1999-2000',' Comedy, Drama',8.8,' 1-18'),(78,'The Way We Live Now',' 2001',' Drama, Romance',7.7,' 1-4'),(79,'Justified',' 2010-2015',' Action, Crime, Drama',8.6,' 1-13, 2-13, 3-13, 4-13, 5-13, 6-13'),(80,'Arrested Development',' 2003- ',' Comedy',8.9,' 1-22, 2-18, 3-13, 4-22, 5-16'),(81,'Stranger Things',' 2016- ',' Drama, Fantasy, Horror',8.9,' 1-8, 2-9'),(82,'Night Court',' 1984-1992',' Comedy',7.7,' 1-13, 2-22, 3-22, 4-22, 5-22, 6-22, 7-24, 8-24, 9-22'),(83,'Revenge',' 2011-2015',' Drama, Mystery, Thriller',7.9,' 1-22, 2-22, 3-22, 4-23'),(84,'Da Ali G Show',' 2003-2004',' Comedy, Talk-show',8,' 1-6, 2-6, 3-6'),(85,'Sex & The City',' 1998-2004',' Comedy, Drama, Romance',7.1,' 1-12, 2-18, 3-18, 4-18, 5-8, 6-20'),(86,'Tyrant',' 2014-2016',' Action, Drama, Thriller',7.8,' 1-10, 2-12, 3-10'),(87,'Billions',' 2016- ',' Drama',8.4,' 1-12, 2-12, 3-12'),(88,'Shameless',' 2011- ',' Comedy, Drama',8.7,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-12, 7-12, 8-12, 9-7'),(89,'Smallville',' 2001-2011',' Adventure, Drama, Romance',7.5,' 1-21, 2-23, 3-22, 4-22, 5-22, 6-22, 7-20, 8-22, 9-21, 19-22'),(90,'The Defenders',' 2017 ',' Action, Adventure, Crime',7.5,' 1-8'),(91,'House',' 2004-2012',' Drama, Mystery',8.8,'  1-22, 2-24, 3-24, 4-16, 5-24, 6-21, 7-23, 8-22'),(92,'Fear The Walking Dead',' 2015-',' Drama, Horror, Sci-fi',7,' 1-6, 2-15, 3-16, 4-16'),(93,'True Blood',' 2008-2014',' Drama, Fantasy, Mystery',7.9,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-10, 7-10'),(94,'GLOW',' 2017- ',' Comedy, Drama, Sport',8.2,' 1-10, 2-10'),(95,'Doctor Thorne',' 2016 ',' Drama',7.3,' 1-3'),(96,'The Civil War',' 1990',' Documentary, History, War',9.1,' 1-9'),(97,'Person Of Interest',' 2011-2016',' Action, Crime, Drama',8.4,' 1-23, 2-22, 3-23, 4-22, 5-13'),(98,'Californication',' 2007-2014',' Comedy, Drama',8.3,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-12, 7-12'),(99,'Three\'s Company',' 1976-1984',' Comedy',7.6,' 1-6, 2-25, 3-22, 4-25, 5-22, 6-28, 7-22, 8-22'),(100,'Dexter',' 2006-2013',' Crime, Drama, Mystery',8.7,' 1-12, 2-12, 3-12, 4-12, 5-12, 6-12, 7-12.8-12');
/*!40000 ALTER TABLE `series` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Jack','Hejsa'),(2,'Julius','bae'),(5,'bjark','hej'),(6,'Faggotjack','jack'),(7,'jack','Hejsa'),(8,'signe','hej'),(9,'s','123'),(10,'h','123'),(11,'j','123');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-01 23:11:01
