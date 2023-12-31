<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:template match="/">
    <html>
      <head>
        <style>
          table {
            font-family: Arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
          }
          th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
          }
          th {
            background-color: #f2f2f2;
          }
        </style>
      </head>
      <body>
        <h2>Diákok</h2>
        <table>
          <tr>
            <th>ID</th>
            <th>Vezeteknev</th>
            <th>Keresztnev</th>
            <th>Becenev</th>
            <th>Kor</th>
            <th>Fizetes</th>
          </tr>
          <xsl:for-each select="//student">
            <tr>
              <td><xsl:value-of select="@id"/></td>
              <td><xsl:value-of select="vezeteknev"/></td>
              <td><xsl:value-of select="keresztnev"/></td>
              <td><xsl:value-of select="becenev"/></td>
              <td><xsl:value-of select="kor"/></td>
              <td><xsl:value-of select="osztondij"/></td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
