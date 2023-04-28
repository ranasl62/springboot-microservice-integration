import React, { useState , useEffect} from 'react';
import axios from 'axios';
import { makeStyles } from '@material-ui/core/styles';
import {
  Container,
  Typography,
  Box,
  Card,
  CardActionArea,
  CardContent,
  Grid,
  AppBar,
  Toolbar,
  IconButton,
  CssBaseline,
  CardMedia,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import cpuImage from './components/cpu.jpg';
import diskImage from './components/disk.jpg';
import networkImage from './components/network.jpg';
import ramImage from './components/ram.jpg'
import './App.css';
import Cpu from'./components/Cpu';
import Disk from'./components/Disk';
import Network from'./components/Network';
import Ram from'./components/Ram';

const useStyles = makeStyles({
  cardMedia: {
    height: 300,
    width: 300,
    borderRadius: 30,
  },
});
const App = () => {
  const classes = useStyles();

  const [streamType, setStreamType] = useState(null);

  const handleClick = (type) => {
    setStreamType(type);
  };

  return (
    <>
          <CssBaseline />
      <AppBar position="static">
        <Toolbar>
          <IconButton
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Auxiliary Data Service
          </Typography>
        </Toolbar>
      </AppBar>
    <Container maxWidth="md">
    <Box sx={{ my: 4 }}>
    <Typography variant="h4" component="h1" gutterBottom>
            Welcome to Auxiliary Data Input Service
          </Typography>
          {!streamType && (
          <Grid container spacing={2}>
            <Grid item xs={6} sm={3}>
              <Card onClick={() => handleClick('cpu')}>
                <CardActionArea>
                  <CardContent>
                    <Typography variant="h5">CPU Metrics</Typography>
                  </CardContent>
                  <CardMedia

                       className={classes.cardMedia}
                      component="img"

                       image={cpuImage}
                       alt="CPU Image"
                  />
                </CardActionArea>
              </Card>
            </Grid>
            <Grid item xs={6} sm={3}>
              <Card onClick={() => handleClick('disk')}>
                <CardActionArea>
                  <CardContent>
                    <Typography variant="h5">Disk Metrics</Typography>
                  </CardContent>
                  <CardMedia
                     className={classes.cardMedia}
                     component="img"
                       image={diskImage}
                       alt="Disk Image"
                  />
                </CardActionArea>
              </Card>
            </Grid>
            <Grid item xs={6} sm={3}>
              <Card onClick={() => handleClick('ram')}>
                <CardActionArea>
                  <CardContent>
                    <Typography variant="h5">RAM Metrics</Typography>
                  </CardContent>
                  <CardMedia
                       className={classes.cardMedia}
                       component="img"
                       image={ramImage}
                       alt="RAM Image"
                  />
                </CardActionArea>
              </Card>
            </Grid>
            <Grid item xs={6} sm={3}>
              <Card onClick={() => handleClick('network')}>
                <CardActionArea>
                  <CardContent>
                    <Typography variant="h5">Network </Typography>
                  </CardContent>
                </CardActionArea>
                <CardMedia
                     className={classes.cardMedia}
                     component="img"
                      image={networkImage}
                      alt="NETWORK Image"
                  />
              </Card>
            </Grid>
          </Grid>
        )}
      {streamType === 'cpu' && <Cpu />}
      {streamType === 'disk' && <Disk />}
      {streamType === 'ram' && <Ram />}
      {streamType === 'network' && <Network/>}
    </Box>
  </Container>
  </>
  );
};

export default App;
