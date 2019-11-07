;(function() {
  'use strict';

  sigma.utils.pkg('sigma.webgl.nodes');

  /**
   * This node renderer will display nodes in the fastest way: Nodes are basic
   * squares, drawn through the gl.POINTS drawing method. The size of the nodes
   * are represented with the "gl_PointSize" value in the vertex shader.
   *
   * It is the fastest node renderer here since the buffer just takes one line
   * to draw each node (with attributes "x", "y", "size" and "color").
   *
   * Nevertheless, this method has some problems, especially due to some issues
   * with the gl.POINTS:
   *  - First, if the center of a node is outside the scene, the point will not
   *    be drawn, even if it should be partly on screen.
   *  - I tried applying a fragment shader similar to the one in the default
   *    node renderer to display them as discs, but it did not work fine on
   *    some computers settings, filling the discs with weird gradients not
   *    depending on the actual color.
   */
  sigma.webgl.nodes.fast = {
    POINTS: 1,
    ATTRIBUTES: 15,
    addNode: function(node, data, i, prefix, settings) {
      data[i++] = node[prefix + 'x'];
      data[i++] = node[prefix + 'y'];
      data[i++] = node[prefix + 'size'];
            
      data[i++] = sigma.utils.floatColor(
        node.colora0 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colorb0 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colorc0 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colord0 || settings('defaultNodeColor')
      );
      
      
      data[i++] = sigma.utils.floatColor(
        node.colora1 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colorb1 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colorc1 || settings('defaultNodeColor')
      );
            
      data[i++] = sigma.utils.floatColor(
        node.colord1 || settings('defaultNodeColor')
      );
      

      
    },
    render: function(gl, program, data, params) {
      var buffer;

      // Define attributes:
      var positionLocation =
            gl.getAttribLocation(program, 'a_position'),
          sizeLocation =
            gl.getAttribLocation(program, 'a_size'),
            

            colorLocationa0 =
                gl.getAttribLocation(program, 'a_color_a_0'),
            colorLocationb0 =
                gl.getAttribLocation(program, 'a_color_b_0'),
            colorLocationc0 =
                gl.getAttribLocation(program, 'a_color_c_0'),
            colorLocationd0 =
                gl.getAttribLocation(program, 'a_color_d_0'),
                
            colorLocationa1 =
                gl.getAttribLocation(program, 'a_color_a_1'),
            colorLocationb1 =
                gl.getAttribLocation(program, 'a_color_b_1'),
            colorLocationc1 =
                gl.getAttribLocation(program, 'a_color_c_1'),
            colorLocationd1 =
                gl.getAttribLocation(program, 'a_color_d_1'),
                
            
          resolutionLocation =
            gl.getUniformLocation(program, 'u_resolution'),
          matrixLocation =
            gl.getUniformLocation(program, 'u_matrix'),
          ratioLocation =
            gl.getUniformLocation(program, 'u_ratio'),
          scaleLocation =
            gl.getUniformLocation(program, 'u_scale');

      buffer = gl.createBuffer();
      gl.bindBuffer(gl.ARRAY_BUFFER, buffer);
      gl.bufferData(gl.ARRAY_BUFFER, data, gl.DYNAMIC_DRAW);

      gl.uniform2f(resolutionLocation, params.width, params.height);
      gl.uniform1f(
        ratioLocation,
        1 / Math.pow(params.ratio, params.settings('nodesPowRatio'))
      );
      gl.uniform1f(scaleLocation, params.scalingRatio);
      gl.uniformMatrix3fv(matrixLocation, false, params.matrix);

      gl.enableVertexAttribArray(positionLocation);
      gl.enableVertexAttribArray(sizeLocation);

      gl.enableVertexAttribArray(colorLocationa0);
      gl.enableVertexAttribArray(colorLocationb0);
      gl.enableVertexAttribArray(colorLocationc0);
      gl.enableVertexAttribArray(colorLocationd0);

      gl.enableVertexAttribArray(colorLocationa1);
      gl.enableVertexAttribArray(colorLocationb1);
      gl.enableVertexAttribArray(colorLocationc1);
      gl.enableVertexAttribArray(colorLocationd1);


      gl.vertexAttribPointer(
        positionLocation,
        2,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        0
      );
      gl.vertexAttribPointer(
        sizeLocation,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        8
      );
      
      

      gl.vertexAttribPointer(
        colorLocationa0,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        12
      );
      gl.vertexAttribPointer(
        colorLocationb0,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        16
      );
      gl.vertexAttribPointer(
        colorLocationc0,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        20
      );
      gl.vertexAttribPointer(
        colorLocationd0,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        24
      );
      
      

      gl.vertexAttribPointer(
        colorLocationa1,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        28
      );
      gl.vertexAttribPointer(
        colorLocationb1,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        32
      );
      gl.vertexAttribPointer(
        colorLocationc1,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        36
      );
      gl.vertexAttribPointer(
        colorLocationd1,
        1,
        gl.FLOAT,
        false,
        this.ATTRIBUTES * Float32Array.BYTES_PER_ELEMENT,
        40
      );
      
      

      
      
      
      gl.drawArrays(
        gl.POINTS,
        params.start || 0,
        params.count || (data.length / this.ATTRIBUTES)
      );
    },
    initProgram: function(gl) {
      var vertexShader,
          fragmentShader,
          program;

      vertexShader = sigma.utils.loadShader(
        gl,
        [
          'attribute vec2 a_position;',
          'attribute float a_size;',
          
          'attribute float a_color_a_0;',
          'attribute float a_color_b_0;',
          'attribute float a_color_c_0;',
          'attribute float a_color_d_0;',
          

          'attribute float a_color_a_1;',
          'attribute float a_color_b_1;',
          'attribute float a_color_c_1;',
          'attribute float a_color_d_1;',
          

          'attribute float a_color_a_2;',
          'attribute float a_color_b_2;',
          'attribute float a_color_c_2;',
          'attribute float a_color_d_2;',

          'uniform vec2 u_resolution;',
          'uniform float u_ratio;',
          'uniform float u_scale;',
          'uniform mat3 u_matrix;',
          
          
          

          'varying vec4 colors[8];',
          'varying vec4 color;',
          'varying vec4 newColor;',

          'void myFunc(float c, out vec4 res){',
                    
	          'vec4 testc;',
	          'testc.b = mod(c, 256.0); c = floor(c / 256.0);',
	          'testc.g = mod(c, 256.0); c = floor(c / 256.0);',
	          'testc.r = mod(c, 256.0); c = floor(c / 256.0);',
	          'testc /= 255.0;',
	          'testc.a = 1.0;',
	          
				'res.r = testc.r;',
				'res.g = testc.g;',
				'res.b = testc.b;',
				'res.a = testc.a;',
				
          ,'}',

          'void main() {',
            // Scale from [[-1 1] [-1 1]] to the container:
            'gl_Position = vec4(',
              '((u_matrix * vec3(a_position, 1)).xy /',
                'u_resolution * 2.0 - 1.0) * vec2(1, -1),',
              '0,',
              '1',
            ');',

            // Multiply the point size twice:
            //  - x SCALING_RATIO to correct the canvas scaling
            //  - x 2 to correct the formulae
            'gl_PointSize = a_size * u_ratio * u_scale * 2.0;',

            'myFunc(a_color_a_0, colors[0]);',
            'myFunc(a_color_b_0, colors[1]);',
            'myFunc(a_color_c_0, colors[2]);',
            'myFunc(a_color_d_0, colors[3]);',
            'myFunc(a_color_a_1, colors[4]);',
            'myFunc(a_color_b_1, colors[5]);',
            'myFunc(a_color_c_1, colors[6]);',
            'myFunc(a_color_d_1, colors[7]);',
            
          '}'
        ].join('\n'),
        gl.VERTEX_SHADER
      );

      fragmentShader = sigma.utils.loadShader(
        gl,
        [
          'precision mediump float;',

          'varying vec4 colors[8];',
          

          'void copyColor(vec4 c, out vec4 res){',
                    
	          
				'res.r = c.r;',
				'res.g = c.g;',
				'res.b = c.b;',
				'res.a = c.a;',
				
          ,'}',
          
          	
          'int loadQuadrant(vec2 point){',
          
          	//'res=0;',

    		'if(point.x<0.5 && point.y <0.5)return 0;',
      		'if(point.x<0.5 && point.y >0.5)return 1;',
      		'if(point.x>0.5 && point.y <0.5)return 2;',
      		'if(point.x>0.5 && point.y >0.5)return 3;',
          
		,'}',
        

        'void colorFunc0( int quadrant){',
        
			'if(quadrant == 0)',  
			  	'copyColor(colors[0], gl_FragColor);',
			'else if(quadrant == 1)',  
			  	'copyColor(colors[1], gl_FragColor);',
			'else if(quadrant == 2)',  
			  	'copyColor(colors[2], gl_FragColor);',
			'else',  
			  	'copyColor(colors[3], gl_FragColor);',
		  	
	          ,'}',
	          

	          'void colorFunc1( int quadrant){',
	          
				'if(quadrant == 0)',  
				  	'copyColor(colors[4], gl_FragColor);',
				'else if(quadrant == 1)',  
				  	'copyColor(colors[5], gl_FragColor);',
				'else if(quadrant == 2)',  
				  	'copyColor(colors[6], gl_FragColor);',
				'else',  
				  	'copyColor(colors[7], gl_FragColor);',
		          ,'}',


          'void main(void) {',
	          'float innerBorder = 0.3;',
	          'float outerBorder = 0.5;',
	

            'vec2 m = gl_PointCoord - vec2(0.5, 0.5);',
            'float dist = sqrt(m.x * m.x + m.y * m.y);',
            
            'int quadrant = loadQuadrant(gl_PointCoord);',
            
            'if (dist < innerBorder)',
            	'colorFunc0(quadrant);',
            'else if (dist < outerBorder)',
              	'colorFunc1(quadrant);',
            'else',
            	'copyColor(vec4(0.0,0.0,0.0,0.0), gl_FragColor);',


            //'gl_FragColor = mix(colors[1], colors[0], t);',
          '}'
        ].join('\n'),
        gl.FRAGMENT_SHADER
      );

      program = sigma.utils.loadProgram(gl, [vertexShader, fragmentShader]);

      return program;
    }
  };
})();