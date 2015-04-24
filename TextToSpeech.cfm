<!--- Declare Variable FormSumit --->
<cfparam name="Form.Submit" type="string" default="" />

<html>
	<head>
		<title> Text to Speech Conversion</title>
	</head>
	<body>
		<cfif len(form.submit)>
			<cfscript>
				obj = CreateObject("java","SimpleTextToSpeech");
				objOne = obj.Init();
				objOne.callTextToSpeechService(form.inputText);
			</cfscript>
			Click on the media to hear the text
			<cfmediaplayer name="Myvideo" source="./output.mp3" width=500 height=400 align="center" quality="high" />
		<cfelse>
			<cfoutput>#form.submit#</cfoutput>
			<h2> Text to Speech Conversion Demo </h2>
			<form action="TextToSpeech.cfm" method="post">
				<label for="inputText"></label>
				<textarea rows="6" cols="multiple" id="inputText" name="inputText" placeholder="Enter text to be converted" value="hello">
				</textarea>
				<br />
				<input type="submit" value="submit" name="submit" />
			</form>
		</cfif>
	</body>
</html>