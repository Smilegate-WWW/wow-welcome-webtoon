import React from 'react';
import { fade, makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import InputBase from '@material-ui/core/InputBase';
import Box from '@material-ui/core/Box';
import PropTypes from 'prop-types';
import LogoutButton from './LogoutButton';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  searchIcon: {
    width: theme.spacing(7),
    height: '55%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: theme.spacing(0, 0, 0, 1),
  },
  search: {
    flexGrow: 1,
    position: 'static',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: fade(theme.palette.common.white, 0.15),
    '&:hover': {
      backgroundColor: fade(theme.palette.common.white, 0.25),
    },
    marginRight: theme.spacing(1),
    marginLeft: 0,
    width: '100%',
    [theme.breakpoints.up('sm')]: {
      marginLeft: theme.spacing(3),
      width: 'auto',
    },
  },
  inputRoot: {
    color: 'inherit',
  },
  inputInput: {
    padding: theme.spacing(1, 1, 1, 7),
    transition: theme.transitions.create('width'),
    width: '100%',
    [theme.breakpoints.up('md')]: {
      width: 300,
    },
  },
  buttonMargin: {
    marginRight: theme.spacing(2),
  },
}));

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <Typography
      component="div"
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box p={2}>{children}</Box>}
    </Typography>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function Header({ authenticated, logout}) {
  const classes = useStyles();
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" color="inherit">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            WWW 만화
          </Typography>
          <div className={classes.search} >
            <div className={classes.searchIcon}>
              <img src='/Icon/searchIcon.png' />
            </div>
            <InputBase
              placeholder="제목/작가로 검색할 수 있습니다."
              classes={{
                root: classes.inputRoot,
                input: classes.inputInput,
              }}
              inputProps={{ 'aria-label': 'search' }}
            />
          </div>
          <div className={classes.buttonMargin}>
            <Button variant="contained" color="primary" href="http://localhost:3000/login" >
              <span style={{ color: "#fafafa", fontWeight: 550 }}>만화 업로드</span>
            </Button>
          </div>
          {authenticated ? (
            <LogoutButton logout={logout} />
          ) :
            <Button variant="contained" color="primary" href="http://localhost:3000/login">
              <span style={{ color: "#fafafa", fontWeight: 550 }}>로그인</span>
            </Button>
          }
        </Toolbar>
      </AppBar>

    </div>
  );
}
