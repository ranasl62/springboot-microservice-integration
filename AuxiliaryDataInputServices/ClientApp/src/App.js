import React, { useState } from 'react';
import {
  Container,
  Typography,
  Box,
  Grid,
  AppBar,
  Toolbar,
  IconButton,
  CssBaseline,
  Button,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { makeStyles } from '@mui/styles';

import Cpu from './components/Cpu';
import Disk from './components/Disk';
import Network from './components/Network';
import Ram from './components/Ram';
import ComputerList from './components/ComputerList';

const useStyles = makeStyles({
  cardMedia: {
    height: 300,
    width: 300,
    borderRadius: 30,
  },
});
var computerID;


const App = () => {
  const classes = useStyles();

  const [selectedComputer, setSelectedComputer] = useState(null);

  const handleComputerClick = (computer) => {
    setSelectedComputer(computer);
  };
  //to render back to the computerList page
  const handleBackButtonClick = () => {
    setSelectedComputer(null);
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
          {selectedComputer ? (
            <>
             <Button
                onClick={handleBackButtonClick}
                color="primary"
                variant="contained"
              >
                Back to Computer List
              </Button>
              <Typography variant="h5" component="h2" gutterBottom>
                {selectedComputer.name} Metrics
              </Typography>
              <Grid container spacing={2}>
                <Grid item xs={12} md={6}>
                  <Cpu computerID={selectedComputer.id} />
                </Grid>
                <Grid item xs={12} md={6}>
                  <Disk computerID={selectedComputer.id} />
                </Grid>
                <Grid item xs={12} md={6}>
                  <Network computerID={selectedComputer.id} />
                </Grid>
                <Grid item xs={12} md={6}>
                  <Ram computerID={selectedComputer.id} />
                </Grid>
              </Grid>
            </>
          ) : (
            <ComputerList onComputerClick={handleComputerClick}/>
          )}
        </Box>
      </Container>
    </>
  );
};

export default App;
