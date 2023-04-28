import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Cpu from './Cpu';
import Disk from './Disk';
import Network from './Network';
import Ram from './Ram';

const MetricsPage = ({ computers }) => {
  const { computerIndex } = useParams();
  const [selectedMetric, setSelectedMetric] = useState(null);
  const computer = computers[computerIndex];

  const handleMetricClick = (metric) => {
    setSelectedMetric(metric);
  };

  useEffect(() => {
    setSelectedMetric(null);
  }, [computerIndex]);

  return (
    <>
      <h2>{computer.name}</h2>
      {!selectedMetric && (
        // ...existing Grid container code for displaying metric cards...
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
      {selectedMetric === 'cpu' && <Cpu apiUrl={computer.metrics.cpu} />}
      {selectedMetric === 'disk' && <Disk apiUrl={computer.metrics.disk} />}
      {selectedMetric === 'ram' && <Ram apiUrl={computer.metrics.ram} />}
      {selectedMetric === 'network' && (
        <Network apiUrl={computer.metrics.network} />
      )}
    </>
  );
};

export default MetricsPage;
