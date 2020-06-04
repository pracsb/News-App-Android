const express = require('express')
const request = require('request')
const app = express()
const port = process.env.PORT || 5000;
const cors = require('cors')
const googleTrends = require('google-trends-api');
 

app.use(cors())

app.get('/', (req, res) => res.send('Hello World!'))

app.get('/guardian_home', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=(sport|business|technology|politics)&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian");
    }
    )
})


app.get('/guardian_sports', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=sport&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian Sport");
    }
    )
})

app.get('/guardian_tech', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=technology&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian Tech");
    }
    )
})

app.get('/guardian_politics', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=politics&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian politics");
    }
    )
})



app.get('/guardian_science', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=science&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian science");
    }
    )
})

app.get('/guardian_business', (req, res) => 
{
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=business&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian business");
    }
    )
})


app.get('/guardian_world', (req, res) => 
{
    console.log("Sending for Guardian World")
    request("https://content.guardianapis.com/search?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&section=world&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian world");
    }
    )
})




app.get('/NY_home', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/home.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY");
      
    }
    )
})



app.get('/NY_world', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/world.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY world");
      
    }
    )
})


app.get('/NY_politics', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/politics.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY politics");
      
    }
    )
})


app.get('/NY_tech', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/technology.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY tech");
      
    }
    )
})

app.get('/NY_sports', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/sports.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY tech");
      
    }
    )
})

app.get('/NY_business', (req, res) => 
{
    request("https://api.nytimes.com/svc/topstories/v2/business.json?api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx", 
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny); 
        }
        console.log("Sending for NY");
      
    }
    )
})


app.get('/ny_search/:q', (req, res) =>
{
    var value = req.params.q;
    request('https://api.nytimes.com/svc/search/v2/articlesearch.json?q='+value+'&api-key=ll4ViUtWB3I00QjxlQ8cRaPojxfsgAfx',
    function(error, response, body)
    {
        console.log(response.statusCode)
        if(!error && response.statusCode == 200)
        {
            var parsedResponse_ny = JSON.parse(body);
            // var results_array_ny = parsedResponse_ny["results"];
            res.send(parsedResponse_ny["response"]); 
        }
        console.log("Sending for NY search");
      
    }
    )
})


app.get('/g_search/:q', (req, res) =>
{
    var value = req.params.q;
    console.log("value = ", value);
    value.replace(" ", "%20");
    console.log("value = ", value);
    request("https://content.guardianapis.com/search?q="+value+"&api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&show-blocks=all",
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian search");
    }
    
    )
})


app.get('/trending', (req,ans) =>
 {  
    var query = req.param('q');
    console.log(query);
    googleTrends.interestOverTime({keyword: query, startTime: new Date("2019-06-01")})
    .then(function(results){
        var res = JSON.parse(results);

    ans.send(res);
    
    })
    .catch(function(err){
    console.error('Oh no there was an error', err);
    })
 });

//  https://content.guardianapis.com/search?order-by=newest&showfields=starRating,headline,thumbnail,short-url&api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19
 
 app.get('/latest', (req,res)=>{
    request("https://content.guardianapis.com/search?order-by=newest&show-fields=starRating,headline,thumbnail,body,short-url&api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian latest");
    }
    )
 })

app.get('/article', (req, res) => 
{
    
    var id = req.param('id');
    request("https://content.guardianapis.com/"+id+"?api-key=5a3df8fa-4b9d-4899-8dad-def25dc1bd19&show-blocks=all", 
    function(error, response, body)
    {
        if(!error && response.statusCode == 200)
        {
            var parsedResponse = JSON.parse(body);
            var results_array = parsedResponse["response"];
            res.send(results_array); 
        }
        console.log("Sending for Guardian article", id);
    }
    )
})



app.listen(port, () => console.log(`Listening on port ${port}`));