import React from 'react';
import {
  Card,
  CardActionArea,
  CardContent,
  CardMedia,
  Grid,
  Typography,
} from '@mui/material';
import { makeStyles } from '@material-ui/core/styles';
import computer1Image from '../images/c1.jpg';
import computer2Image from '../images/c2.jpg';
import computer3Image from '../images/c3.jpg';
import computer4Image from '../images/c4.jpg';
import computer5Image from '../images/c5.jpg';
import computer6Image from '../images/c6.jpg';
import { useHistory } from 'react-router-dom';

var computers = [
  {
    id: 2,
    name: 'Somal\'s Computer',
    image: computer1Image,
  },
  {
    id: 2,
    name:'Rana\'s Computer',
    image: computer2Image,
  },
  {
    id: 2,
    name: 'Bari\'s Computer',
    image: computer3Image,
  },
  {
    id: 2,
    name: 'Jalal\'s Computer',
    image: computer4Image,
  },
  {
    id: 2,
    name:'Amit\'s Computer',
    image: computer5Image,
  },
  {
    id: 2,
    name: 'Yasmin\'s Computer',
    image: computer6Image,
  },
];
const useStyles = makeStyles({
  cardMedia: {
    height: 300,
    width: 300,
    borderRadius: 30,
  },
});

const ComputerList = ({ onComputerClick }) => {
  const classes = useStyles();
  return (
    <Grid container spacing={2}>
      {computers.map((computer) => (
        <Grid item xs={12} md={4} key={computer.id}>
          <Card onClick={() => onComputerClick(computer)}>
            <CardActionArea>
              <CardMedia
              
               className={classes.cardMedia}
               component="img"
                image={computer.image}
                alt={computer.name}
            
              />
              <CardContent>
                <Typography variant="h6">{computer.name}</Typography>
              </CardContent>
            </CardActionArea>
          </Card>
        </Grid>
      ))}
    </Grid>
  );
};

export default ComputerList;
