import React from 'react';

const Notification = ({ message, type }) => (
  <div className={`alert alert-${type} fixed-top`} style={{ marginTop: '60px' }}>
    {message}
  </div>
);

export default Notification;